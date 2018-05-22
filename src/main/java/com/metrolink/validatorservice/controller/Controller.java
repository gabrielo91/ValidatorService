/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.AlarmsStack;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidations;
import com.metrolink.validatorservice.db.daos.DAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.DAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.DAOSuministros;
import com.metrolink.validatorservice.db.daos.IDAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.IDAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.IDAOSuministros;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidationsSCO;
import com.metrolink.validatorservice.db.daos.DAOMovProcessRegistry;
import com.metrolink.validatorservice.db.daos.DAOParametrosConf;
import com.metrolink.validatorservice.db.daos.IDAOParametrosConf;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovProcessRegistry;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import com.metrolink.validatorservice.utils.CustomComparator;
import java.util.Collections;

/**
 *
 * @author Gabriel Ortega
 */
public class Controller {

    private static final int UN_DIA = 1;
    private IDAOLecturas daoLecturas;
    private IDAOParametrosAdmin daoParametrosAdmin;
    private IDAOAgendaLectura daoAgendaLectura;
    private IDAOSuministros dAOSuministros;

    private final IIndividualValidations idividualValidationsClass;
    private final IIndividualValidationsSCO generalValidationsClass;
    private IPreferencesManager preferencesManager;

    public Controller(IIndividualValidations idividualValidationsClass, IIndividualValidationsSCO generalValidations, IPreferencesManager preferencesManager) {
        this.idividualValidationsClass = idividualValidationsClass;
        this.generalValidationsClass = generalValidations;
        this.preferencesManager = preferencesManager;
    }

    public void startValidationProcess() throws Exception {
        ArrayList<AgendaLectura> itinerariosMovLectConsu = getValuesForChecking();
        ArrayList<AgendaLectura> itinerariosMovRegsSco = getValuesForChecking();
        System.out.println("El tamano es: : " + itinerariosMovLectConsu.size());
        AgendaStack.getInstance().setAgendaValues(itinerariosMovLectConsu);
        lockUnlockSuministros(DAOSuministros.BLOQUEADO);
        performIndividualValidations();
        ArrayList<MovSuministrosPK> suministrosInvalidos = getSuministrosInvalidos(AgendaStack.getInstance().getItinerarios());
        performIndividualValidationsSCO(itinerariosMovRegsSco, suministrosInvalidos);
        certificarLecturas();
        lockUnlockSuministros(DAOSuministros.DESBLOQUEADO);
        String processId = getProcessId();
        saveAlarmas(processId);
    }

    /**
     * This method gets the needed value to analyze
     *
     * @return
     */
    public ArrayList<AgendaLectura> getValuesForChecking() throws Exception {

        ArrayList<AgendaLectura> itinerarios = null;
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoParametrosAdmin = new DAOParametrosAdmin(databaseController);
        MParametrosAdm parametrosAdm = daoParametrosAdmin.getParametrosAdm().get(0);        
        short diasABuscar = parametrosAdm.getNdiasBusca();
        System.out.println("dias busca es: " + diasABuscar);
        daoLecturas = new DAOLecturas(databaseController);
        daoAgendaLectura = new DAOAgendaLectura(databaseController);
        Date startingDate = new Date();
        Date endingDate = Utils.addDays(startingDate, diasABuscar);//addDays
        startingDate = Utils.addDays(startingDate, - UN_DIA);       
        itinerarios = daoAgendaLectura.listAgendaBetweenDates(startingDate, endingDate, DAOAgendaLectura.CONSULTA_MOV_LECT_CONSU);
        //itinerarios = daoAgendaLectura.listAgendaBetweenDates(Utils.addDays(startingDate, -14), Utils.addDays(endingDate, -14), DAOAgendaLectura.CONSULTA_MOV_LECT_CONSU);
        return itinerarios;
    }

    /**
     * This method gets agenda values and performs validations over them
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     */
    public void performIndividualValidations() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, Exception {
        int j = 0;
        for (AgendaLectura intinerario : AgendaStack.getInstance().getItinerarios()) {            
            for (int i = 0; i < intinerario.getListaSuministros().size(); i++) {
                performIndividualValidations(j);
            }
            j++;
        }
    }

    /**
     * Iterate over each method declared in IIndividualValidations interface and
     * uses it over each suministros object. It uses the full array in order to
     * perfom validations which uses previous values
     *
     * @param indexToValidate
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     */
    private void performIndividualValidations(int indexToValidate) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Class validations = idividualValidationsClass.getClass();
        ArrayList<Method> sortedValidations = sortValidations(IIndividualValidations.class.getMethods());
        for (Method bussinesValidation : sortedValidations) {
            System.out.println("La validacion a ejecutar es: " + bussinesValidation.getName());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class);
            validation.invoke(idividualValidationsClass, AgendaStack.getInstance().getItinerarios().get(indexToValidate).getListaSuministros());
        }
    }

    private void lockUnlockSuministros(final short state) throws Exception {
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoParametrosAdmin = new DAOParametrosAdmin(databaseController);
        dAOSuministros = new DAOSuministros(databaseController);
        daoLecturas = new DAOLecturas(databaseController);

        for (AgendaLectura intinerario : AgendaStack.getInstance().getItinerarios()) {

            dAOSuministros.lockUnlockSuministros(intinerario.getListaSuministros(), state);
            for (MovSuministros suministro : intinerario.getListaSuministros()) {
                daoLecturas.lockUnlockLecturas(suministro.getMovLectConsuCollection(), state);
            }
        }
    }

    private void performIndividualValidationsSCO(ArrayList<AgendaLectura> itinerariosMovRegsSco, ArrayList<MovSuministrosPK> suministrosInvalidos) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (AgendaLectura intinerario : itinerariosMovRegsSco) {
            Class validations = generalValidationsClass.getClass();
            
            for (Method bussinesValidation : IIndividualValidationsSCO.class.getMethods()) {
               // System.out.println("La validacion a ejecutar es: " + bussinesValidation.getName());
                Method validation = validations.getMethod(bussinesValidation.getName(), List.class, List.class);
                validation.invoke(generalValidationsClass, intinerario.getListaSuministros(), suministrosInvalidos);
            }

        }
    }

    private void saveAlarmas(String processId) throws Exception {
        try {
            IDatabaseController databaseController = new DatabaseController(preferencesManager);
            AlarmsManager.saveAlarms(databaseController, processId);
        } catch (Exception ex) {
            String mensaje = "Error guardando alarmas";
            DataLogger.Log(ex, mensaje);
            throw new Exception(mensaje);
        }
    }

    private void certificarLecturas() {
        int lecturasCertificadas = 0;
        int lecturasGenraronAlarma = 0;
        int totalSuministros = 0;

        //Se evaluan todos los suministros del stack y se comparan con el stack de alarmas, si no generaron una ce certifican
        ArrayList<AgendaLectura> agendaList = AgendaStack.getInstance().getItinerarios();
        ArrayList<MovSuministrosPK> suministrosInvalidos = getSuministrosInvalidosFromAlarmas();
        for (AgendaLectura agenda : agendaList) {
            totalSuministros = agenda.getListaSuministros().size();
            ArrayList<MovSuministros> itinerarios = agenda.getListaSuministros();
            for (MovSuministros suministro : itinerarios) {
                if (Utils.esItinerarioValido(suministro.getMovSuministrosPK(), suministrosInvalidos)) {
                    lecturasCertificadas = lecturasCertificadas + 1;
                    //TODO certificar
                } else {
                    lecturasGenraronAlarma = lecturasGenraronAlarma + 1;
                }
            }
        }

        System.out.println("El total de suministros que generaron alarma es:  " + lecturasGenraronAlarma);
        System.out.println("El total de suministros certificados es: " + lecturasCertificadas);
        System.out.println("El total de suministros es: " + totalSuministros);
        System.out.println("El total de alarmas es: " +  AlarmsStack.getInstance().getAlarmsStack().size());

    }

    /**
     * Retorna un arreglo con los pk itinerarios que no hayan sido validados
     *
     * @param itinerarios
     * @return
     */
    private ArrayList<MovSuministrosPK> getSuministrosInvalidos(ArrayList<AgendaLectura> agendas) {
        ArrayList<MovSuministrosPK> suministrosInvalidos = new ArrayList<>();
        for (AgendaLectura agenda : agendas) {
            for (MovSuministros suministrosInvalido : agenda.getListaSuministros()) {
                if (suministrosInvalido.isSuministroInvalidado()) {
                    suministrosInvalidos.add(suministrosInvalido.getMovSuministrosPK());
                }
            }
        }
        return suministrosInvalidos;
    }

    /**
     * Retorna los PK del stack de alarmas almacenado
     * @return 
     */
    private ArrayList<MovSuministrosPK> getSuministrosInvalidosFromAlarmas() {
        ArrayList<MovSuministrosPK> suministrosInvalidos = new ArrayList<>();
        for (MovAlarmas alarma : AlarmsStack.getInstance().getAlarmsStack()) {
            suministrosInvalidos.add(alarma.getMovSuministrosPK());
        }
        return suministrosInvalidos;
    }

    /**
     * Retorna el ultimo process ID registrado en la base de datos
     * @return
     * @throws Exception 
     */
    private String getProcessId()  throws Exception {
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        String processId = new DAOMovProcessRegistry(databaseController).getProcessRgistry();
        return processId;
    }

    private ArrayList<Method> sortValidations(Method[] methods) {
        ArrayList<Method> sortedMethods = new ArrayList<>();
        Collections.addAll(sortedMethods, methods);
        Collections.sort(sortedMethods , new CustomComparator());
        return sortedMethods;
    }

}
