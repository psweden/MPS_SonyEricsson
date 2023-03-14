package mps_sonyericsson;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import ConnectFactory.ConnectServer;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordEnumeration;
import java.util.TimeZone;
import java.util.Date;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import grafhics.*;
import java.io.InputStream;
import java.util.Calendar;

public class MobilePersonalState extends MIDlet implements CommandListener,
        Runnable { // programmet inneh�ller tr�dar som startar med sk. synkad start

    private Display displayen; // instansvariabler
    private List listan, listVal;
    public String star, vKopplad, ank, smsNumber;

    private String SerieNumber = "00460101501594500"; // Linus > IMEI 354416-00-839957-8-09
    private String identy, checkIdenty; // Emulator > 00460101501594500 // Oscar > IMEI 35400700-977717-5-09 359315000298736

    private String sortString;
    private String[] subStr;

    // ---------------- Text Bilder ----------------------------------------------------------------
    // The Canvases used to demonstrate different Items

    private grafhics.AboutUs imageGC;

    //----------------------------------------------------------------------------------------------


    private Command // KOMMANDON
            exitListCommand, sendListCommand, goToListHelpFormCommand, // kommandon f�r huvudf�nstret listan
    goToListAboutFormCommand, goToListLoginLogInFormCommand,
    goToBackInfoCommand, goToBackInfoHelpCommand, propertiesCommand,

    helpFormBackCommand, // kommando f�r hj�lp

    aboutFormBackCommand, // kommando f�r om MPS

    logInbackCommand, loggInCommand, // kommandon f�r login-f�nstret

    settingsCancelCommand, settingsChangeCommand, // kommandon f�r setting-f�nstret

    editSettingCancelCommand, editSettingSaveCommand, editSettingBackCommand, // kommandon f�r edit-vyn-f�nstret
    editInfoSettingBackCommand, editSettingInfoCommand,

    lunchSendCommand, lunchInfoCommand, lunchBackCommand, lunchInfoBackCommand, // kommandon f�r lunch-formen.

    upptagenSendCommand, upptagenInfoCommand, upptagenBackCommand,
    upptagenInfoBackCommand, // kommandon f�r formen.

    ringSendCommand, ringInfoCommand, ringBackCommand, ringInfoBackCommand, // kommandon f�r formen.

    ankSendCommand, ankInfoCommand, ankBackCommand, ankInfoBackCommand, // kommandon f�r formen.

    semSendCommand, semInfoCommand, semBackCommand, semInfoBackCommand, // kommandon f�r formen.

    tillbakaSendCommand, tillbakaInfoCommand, tillbakaBackCommand,
    tillbakaInfoBackCommand, // kommandon f�r formen.

    tillbakaKlSendCommand, tillbakaKlInfoCommand, tillbakaKlBackCommand,
    tillbakaKlInfoBackCommand,

    backFromServerNumberClassCommand, vidKoppCommand, taBortVidKoppCommand; // kommandon f�r formen.

    private ImageItem imageItem1;
    private Command thCmd;
    private ConnectServer server;

    private Form loginForm, settingsForm, editSettingForm, editSettingsInfoForm;

    private Alert alertSender, alertLoggOff, alertEditSettings, alertGetStatus;
    private Font fontFace;
    private Date today;
    private TimeZone tz = TimeZone.getTimeZone("GMT+1");
    private DateField datefieldClock, datefieldClock1, datefieldClock2,
    datefieldClock3, datefieldClock4, datefieldClock5, datefieldClock6;

// ------------------ INSTANSER F�R RMS-STORE ----------------------------------

    public RecordStore recStore = null;
    static final String REC_STORE = "Data_Store_attendant_01"; // ska heta DataStore sen

    private TextField editUserName, editUserPassWord, editSmsNumber,
    editVidareKoppling, userName, userPassWord;

    private TextBox textBoxLunch, textBoxSemester, textBoxTillbakaKlockan,
    textBoxTillbakaDen, textBoxRing, textBoxAnknytning, textBoxUpptagen;
    private String Uname, passWord;
    private String lunchStatus, mellanslag = " ";

// nytttttttt

    private TextBox dialTextBox;

    private Command DialCommand, ExitCommand, AboutCommand,
    minimazeCommand, helpCommand, backCommand, settingsCommand;

    private StringItem item;
    private String stringTotal;
    private int type = 0;
    private String SOS;
    private String setP = ";postd="; //;postd= // /p

    private String IMEI; // oscar > 35400700977717509

    private String accessNumber, switchBoardNumber, setMounth, setDate, setYear,
    DBdate, DBmounth, DByear, DBdateBack, DBmounthBack, DByearBack, getTWO,
    dateString, setViewMounth, ViewDateString, setdayBack,
    setmounthBack, setyearBack;


    private TextField dateNumber, accessNumbers, editSwitchBoardNumber;


    private int antalDagar = 30; // anger hur m�nga dagar programmet ska vara �ppet innan det st�ngs....
    private int dayBack;
    private int mounthBack;
    private int yearBack;
    private int dayAfter;
    private int monthAfter;
    private int yearAfter;
    private int day;
    private int month;
    private int checkYear;

    private String s1;
    private String s2;


// ----------- CONTSTRUKTOR s�tter egenskaper ----------------------------------

    public MobilePersonalState() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException { // Constructor f�r att s�tta attributens egenskaper

        this.tz = tz;

        today = new Date();
        today.getTime();
        today.toString();

        System.out.println(today);

        try {
            this.smsNumber = getSwitchBoardNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        this.star = star;
        this.lunchStatus = lunchStatus;
        this.identy = ""; // System.getProperty("com.sonyericsson.imei");
        this.checkIdenty = checkIdenty;
        this.SerieNumber = SerieNumber.trim();
        try {
            this.ank = getAccessNumber();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        this.Uname = Uname;

        //validateIMEI();

//----------------- FONT -------------------------------------------------------
        this.fontFace = fontFace;
        this.imageGC = imageGC;

// nyyyyyttttttttttttt

        this.antalDagar = antalDagar;

        //this.accessNumber = accessNumber;

        this.setP = setP;
        this.SOS = "112";
        this.identy = ""; // System.getProperty("com.sonyericsson.imei");
        this.checkIdenty = checkIdenty;
        this.SerieNumber = SerieNumber.trim();
        this.IMEI = identy;
        this.star = "1";

        this.day = day;
        this.month = month;
        setDBDate(); // OBS.. Det h�r metodanropet ska ligga h�r efter month och day.
        setDBDateBack();

        //---------------- Tillbaka den --------------------------------------------


        textBoxTillbakaDen = new TextBox("Skriv in MMDD", "", 4,
                                         TextField.PHONENUMBER);

        datefieldClock6 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock6.setDate(today);

        tillbakaSendCommand = new Command("H�nvisa", Command.OK, 1);
        tillbakaInfoCommand = new Command("Info", Command.HELP, 2);
        tillbakaBackCommand = new Command("Bak�t", Command.BACK, 3);
        tillbakaInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxTillbakaDen.addCommand(tillbakaSendCommand);
        //textBoxTillbakaDen.addCommand(tillbakaInfoCommand);
        textBoxTillbakaDen.addCommand(tillbakaBackCommand);
        textBoxTillbakaDen.setCommandListener(this);

//---------------- Semester tillbaka den -------------------------------------------


        textBoxSemester = new TextBox("Skriv in MMDD", "", 4,
                                      TextField.PHONENUMBER);

        datefieldClock5 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock5.setDate(today);

        semSendCommand = new Command("H�nvisa", Command.OK, 1);
        semInfoCommand = new Command("Info", Command.HELP, 2);
        semBackCommand = new Command("Bak�t", Command.BACK, 3);
        semInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxSemester.addCommand(semSendCommand);
        //textBoxSemester.addCommand(semInfoCommand);
        textBoxSemester.addCommand(semBackCommand);
        textBoxSemester.setCommandListener(this);

//---------------- Upptagen till Klockan --------------------------------------------


        textBoxUpptagen = new TextBox("Skriv in TTMM", "", 4,
                                      TextField.PHONENUMBER);

        datefieldClock2 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock2.setDate(today);

        upptagenSendCommand = new Command("H�nvisa", Command.OK, 1);
        upptagenInfoCommand = new Command("Info", Command.HELP, 2);
        upptagenBackCommand = new Command("Bak�t", Command.BACK, 3);
        upptagenInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxUpptagen.addCommand(upptagenSendCommand);
        //textBoxUpptagen.addCommand(upptagenInfoCommand);
        textBoxUpptagen.addCommand(upptagenBackCommand);
        textBoxUpptagen.setCommandListener(this);

//---------------- Anknytning --------------------------------------------------------


        textBoxAnknytning = new TextBox("Ring XXXX Max 3 Siffror", "", 3,
                                        TextField.PHONENUMBER);

        datefieldClock4 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock4.setDate(today);

        ankSendCommand = new Command("H�nvisa", Command.OK, 1);
        ankInfoCommand = new Command("Info", Command.HELP, 2);
        ankBackCommand = new Command("Bak�t", Command.BACK, 3);
        ankInfoBackCommand = new Command("Tillbaka", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxAnknytning.addCommand(ankSendCommand);
        //textBoxAnknytning.addCommand(ankInfoCommand);
        textBoxAnknytning.addCommand(ankBackCommand);
        textBoxAnknytning.setCommandListener(this);

//---------------- Ring --------------------------------------------------------


        textBoxRing = new TextBox("Ring XXXX Max 17 Siffror", "", 17,
                                  TextField.PHONENUMBER);

        datefieldClock3 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock3.setDate(today);

        ringSendCommand = new Command("H�nvisa", Command.OK, 1);
        ringInfoCommand = new Command("Info", Command.HELP, 2);
        ringBackCommand = new Command("Bak�t", Command.BACK, 3);
        ringInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxRing.addCommand(ringSendCommand);
        //textBoxRing.addCommand(ringInfoCommand);
        textBoxRing.addCommand(ringBackCommand);
        textBoxRing.setCommandListener(this);

//---------------- Tillbaka Klockan --------------------------------------------


        textBoxTillbakaKlockan = new TextBox("Skriv in TTMM", "", 4,
                                             TextField.PHONENUMBER);

        datefieldClock1 = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock1.setDate(today);

        tillbakaKlSendCommand = new Command("H�nvisa", Command.OK, 1);
        tillbakaKlInfoCommand = new Command("Info", Command.HELP, 2);
        tillbakaKlBackCommand = new Command("Bak�t", Command.BACK, 3);
        tillbakaKlInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxTillbakaKlockan.addCommand(tillbakaKlSendCommand);
        //textBoxTillbakaKlockan.addCommand(tillbakaKlInfoCommand);
        textBoxTillbakaKlockan.addCommand(tillbakaKlBackCommand);
        textBoxTillbakaKlockan.setCommandListener(this);

//---------------- Lunch Form --------------------------------------------------

        textBoxLunch = new TextBox("Skriv in TTMM", "", 4,
                                   TextField.PHONENUMBER);

        datefieldClock = new DateField("Tid och Datum", DateField.DATE_TIME);
        datefieldClock.setDate(today);

        lunchSendCommand = new Command("H�nvisa", Command.OK, 1);
        lunchInfoCommand = new Command("Info", Command.HELP, 2);
        lunchBackCommand = new Command("Bak�t", Command.BACK, 3);
        lunchInfoBackCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        textBoxLunch.addCommand(lunchSendCommand);
        //textBoxLunch.addCommand(lunchInfoCommand);
        textBoxLunch.addCommand(lunchBackCommand);
        textBoxLunch.setCommandListener(this);

//---------------- EDITSETTINGFORM ---------------------------------------------

        editSettingForm = new Form("Egenskaper");

        editSettingForm = new Form("Egenskaper");

        dateNumber = new TextField("dagensdatum: ", "", 32, TextField.ANY);
        accessNumbers = new TextField("Anknytning nr: ", "", 32,
                                      TextField.NUMERIC);

        editSwitchBoardNumber = new TextField("GSM-Server tel: ", "", 32,
                                              TextField.PHONENUMBER);

        editSettingBackCommand = new Command("Bak�t", Command.BACK, 1);
        editSettingCancelCommand = new Command("Avbryt", Command.BACK, 1);
        editSettingSaveCommand = new Command("Spara", Command.OK, 2);
        editSettingInfoCommand = new Command("Info", Command.HELP, 3);

        //editSettingForm.addCommand(editSettingInfoCommand);
        editSettingForm.addCommand(editSettingBackCommand);
        editSettingForm.addCommand(editSettingCancelCommand);
        editSettingForm.addCommand(editSettingSaveCommand);
        editSettingForm.setCommandListener(this);

//---------------- SETTING-FORM ------------------------------------------------

        settingsForm = new Form("");

        settingsCancelCommand = new Command("Bak�t", Command.BACK, 1);
        settingsForm.addCommand(settingsCancelCommand);
        settingsForm.setCommandListener(this);

//--------------- CHOICE-LIST --------------------------------------------------

        listVal = new List("", Choice.EXCLUSIVE); //choice-list f�r att skicka vidarekoppling till classen servernummer

        listVal.setTitle("Talad H�nvisning");
        listVal.append("Aktivera", null);
        listVal.append("Avaktivera", null);

        vidKoppCommand = new Command("Spara", Command.OK, 1);
        taBortVidKoppCommand = new Command("Avbryt", Command.BACK, 1);

        listVal.addCommand(vidKoppCommand);
        listVal.addCommand(taBortVidKoppCommand);
        listVal.setCommandListener(this);

//---------------- LOGIN FORM --------------------------------------------------

        loginForm = new Form("Login Mobile-PS");

        userName = new TextField("Username", "", 32, TextField.ANY);
        userPassWord = new TextField("Password", "", 32, TextField.PASSWORD);

        loggInCommand = new Command("Logga In", Command.OK, 2);
        logInbackCommand = new Command("Bak�t", Command.BACK, 1);

        loginForm.addCommand(loggInCommand);
        loginForm.addCommand(logInbackCommand);
        loginForm.setCommandListener(this);

// --------------- EDIT-SETTINGS-INFO-FORM -------------------------------------

        editSettingsInfoForm = new Form("");

        editInfoSettingBackCommand = new Command("Bak�t", Command.BACK, 1);

        editSettingsInfoForm.addCommand(editInfoSettingBackCommand);
        editSettingsInfoForm.setCommandListener(this);

//------------------------ ALERT-SCREEN'S --------------------------------------

        alertSender = new Alert("S�nd SMS", "\n\n\n.....S�nder...SMS...... ",
                                null, AlertType.CONFIRMATION);
        alertSender.setTimeout(2000);

        alertLoggOff = new Alert("Loggar Ut", "\n\n\n...Du Loggas Ut... ",
                                 null, AlertType.CONFIRMATION);
        alertLoggOff.setTimeout(2000);

        alertGetStatus = new Alert("H�mtar Status",
                                   "Ditt status h�mtas \nfr�n servern! ",
                                   null, AlertType.CONFIRMATION);
        alertGetStatus.setTimeout(Alert.FOREVER);
        //alertLoggOff.;

        alertEditSettings = new Alert("Sparar �ndringar",
                                      "\n\n\n...�ndringar sparas... ",
                                      null, AlertType.CONFIRMATION);
        alertEditSettings.setTimeout(2000);

//--------------- HUVUD-VIEW-LISTAN --------------------------------------------

        listan = new List("MPS Ver 1.0", Choice.EXCLUSIVE); // skapar en lista

        // skapar nya kommandonknappar till listan

        exitListCommand = new Command("Avsluta", Command.EXIT, 1);
        //addNumbercommand = new Command("Anknytning", Command.EXIT, 2);
        sendListCommand = new Command("H�nvisa", Command.OK, 3);
        goToListHelpFormCommand = new Command("Hj�lp", Command.HELP, 4);
        goToListAboutFormCommand = new Command("Om MPS", Command.HELP, 5);
        goToListLoginLogInFormCommand = new Command("Inst�llningar", Command.OK,
                6);

        goToBackInfoCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.
        goToBackInfoHelpCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        backFromServerNumberClassCommand = new Command("Bak�t", Command.BACK, 4); // Kommando h�r till graphic-vyn f�r infot.

        listan.append("Lunch Tillbaka Klockan", null); // skickar text-sms
        listan.append("Semester Tillbaka Den", null); // skickar text-sms
        listan.append("Tillbaka Klockan", null); // skickar text-sms
        listan.append("Tillbaka Den", null); // skickar text-sms
        listan.append("Ute", null); // Tid
        listan.append("Ring", null); // skickar text-sms
        listan.append("Finns P� Anknytning", null); // skickar text-sms
        listan.append("Upptagen Till Klockan", null); // skickar text-sms
        listan.append("Sjuk", null); // Tid
        listan.append("Bortrest", null); // datum och tid
        listan.append("V�rd av Barn", null); // datum och tid
        listan.append("Ta Bort H�nvisning", null); // datum och tid
        listan.append("Talad H�nvisning", null); // datum och tid

        // l�gger till knappar till displayen
        listan.addCommand(goToListLoginLogInFormCommand);
        listan.addCommand(exitListCommand);
        listan.addCommand(sendListCommand);
        listan.setCommandListener(this);

// -------- KODEN TILLH�R F�R ATT S�TTA TEX. IMAGE -----------------------------
        setDataStore();
        //controllString();
       // controllDate();
        //this.ViewDateString = setViewDateString();

        if(ViewDateString == null){

            this.ViewDateString = "Enterprise License";

        }


        try {
            DateAndTimeItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// ---------------- METODER ----------------------------------------------------
    public void tryAgainLunch() { // METODEN TILLH�R LUHCN-STATUS
        Alert error = new Alert("Felmarkerat! ", "F�rs�k igen...", null,
                                AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        userName.setString("");
        userPassWord.setString("");

    }

    public void tryAgainMobil() { // METODEN TILLH�R MOBIL-STATUS
        Alert error2 = new Alert("Felmarkerat! ", "F�rs�k igen...", null,
                                 AlertType.ERROR);
        error2.setTimeout(Alert.FOREVER);
    }

    public void tryAgain() { // METODEN TILLH�R INLOGGINGEN (egenskaper)
        Alert error = new Alert("Login Incorrect", "Please try again", null,
                                AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        userName.setString("");
        userPassWord.setString("");

        displayen.setCurrent(error, loginForm);
    }

    public String sortCharAt(String s) {

        this.sortString = identy; // sortString inneh�ller samma som f�r IMEI-str�ngen f�r att kunna kontrollera � sortera bort tecken....

        StringBuffer bTecken = new StringBuffer(sortString); // L�gg str�ngen sortString i ett stringbuffer objekt...

        for (int i = 0; i < bTecken.length(); i++) { // r�kna upp hela bTecken-str�ngens inneh�ll hela dess l�ngd

            char tecken = bTecken.charAt(i); // char tecken �r inneh�llet i hela l�ngden

            if ('A' <= tecken && tecken <= 'Z' ||
                'a' <= tecken && tecken <= 'z' // Sorterar ur tecken ur IMEI-str�ngen
                || tecken == '-' || tecken == '/' || tecken == '\\' ||
                tecken == ':' || tecken == ';'
                || tecken == '.' || tecken == ',' || tecken == '_' ||
                tecken == '|' || tecken == '<'
                || tecken == '>' || tecken == '+' || tecken == '(' ||
                tecken == ')') {

                bTecken.setCharAt(i, ' '); // l�gg in blanksteg i IMEI-str�ngen d�r n�got av ovanst�ende tecken finns....
            }

        }

        bTecken.append(' '); // l�gger till blanksteg sist i raden s� att sista kommer med f�r att do-satsen ska kunna hitta och sortera...

        String setString = new String(bTecken); // G�r om char-str�ngen till en string-str�ng

        int antal = 0;
        char separator = ' '; // f�r att kunna sortera i do-satsen

        int index = 0;

        do { // do satsen sorterar ut blankstegen och g�r en ny str�ng f�r att j�mf�ra IMEI med...
            ++antal;
            ++index;

            index = setString.indexOf(separator, index);
        } while (index != -1);

        subStr = new String[antal];
        index = 0;
        int slutindex = 0;

        for (int j = 0; j < antal; j++) {

            slutindex = setString.indexOf(separator, index);

            if (slutindex == -1) {
                subStr[j] = setString.substring(index);
            }

            else {
                subStr[j] = setString.substring(index, slutindex);
            }

            index = slutindex + 1;

        }
        String setNumber = "";
        for (int k = 0; k < subStr.length; k++) {

            setNumber += subStr[k]; // L�gg in v�rdena fr�n subStr[k] i str�ngen setNumber....
        }

        System.out.println("Sorterad: " + setNumber);

        System.out.println("" + identy);

        String sendIMEI = setNumber;

        return sendIMEI;
    }

    public void validateIMEI() {
        //Identifierar serienumret IMEI...

        String validate = setIMEI(checkIdenty);

        if (!SerieNumber.equals(validate)) {

            System.out.println("FALSK");
            notifyDestroyed();

        } else {

            System.out.println("SANN");
        }

    }

    public String setIMEI(String totalIMEI) { // IMEI 00460101-501594-5-00

        String checkIMEI = "";

        totalIMEI = sortCharAt(checkIMEI);

        String TotalIMEI = totalIMEI;
        TotalIMEI.trim();

        return TotalIMEI;
    }

    public TextBox getTextBoxUpptagen() {

        textBoxUpptagen.setString("");

        return textBoxUpptagen;
    }

    public TextBox getTextBoxAnknytning() {

        textBoxAnknytning.setString("");

        return textBoxAnknytning;
    }

    public TextBox getTextBoxRing() {

        textBoxRing.setString("");

        return textBoxRing;
    }

    public TextBox getTextBoxTillbakaDen() {

        textBoxTillbakaDen.setString("");

        return textBoxTillbakaDen;
    }


    public TextBox getTextBoxLunch() {

        textBoxLunch.setString("");

        return textBoxLunch;
    }

    public TextBox getTextBoxSemester() {

        textBoxSemester.setString("");

        return textBoxSemester;
    }

    public TextBox getTextBoxTillbakaKlockan() {

        textBoxTillbakaKlockan.setString("");

        return textBoxTillbakaKlockan;
    }


    public String getLabel(String l) {

        String todays = today.toString();

        return todays;
    }

    public Form getSettingsForm() { // METODEN RETURNERAR FORMEN F�R SETTING-FORM

        userName.setString("");
        userPassWord.setString("");

        settingsForm.deleteAll();

        settingsForm.append("\nServer Nummer: " + smsNumber + "\n");

        return settingsForm;
    }

    public Form getLogInForm() { // METODEN RETURNERAR FORMEN F�R LOGIN-FORM

        loginForm.deleteAll();
        loginForm.append(userName);
        loginForm.append(userPassWord);

        return loginForm;
    }

    public Form getEditSettingInfoForm() { // METODEN RETURNERAR FORMEN F�R EDIT-INFOT

        editSettingsInfoForm.deleteAll();

        editSettingsInfoForm.setTitle("Info Om Editsettings");
        editSettingsInfoForm.append("\nInfo");

        return editSettingsInfoForm;
    }

    public void commandAction(Command c, Displayable d) { // S�TTER COMMAND-ACTION STARTAR TR�DETS KOMMANDON (tr�dar)
        Thread th = new Thread(this);
        thCmd = c;
        th.start();
    }

    public void startApp() throws MIDletStateChangeException { // Applikationens main-metod
        try {
            //  Get the LCDI Display context, startar displayen
            if (displayen == null) {
                //createCanvases(); // Anropa metoden skapa instansen bakgrunden

                try {
                    setDataStore();
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreNotOpenException ex) {
                } catch (RecordStoreException ex) {
                }
                try {
                    upDateDataStore();
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }
                try {
                    controllString();
                } catch (InvalidRecordIDException ex2) {
                } catch (RecordStoreNotOpenException ex2) {
                } catch (RecordStoreException ex2) {
                }

                displayen = Display.getDisplay(this);
                displayen.setCurrent(listan);

            }
            if (server == null) { // startar servern
                server = server.getInstance(displayen);

                if (server != null) {
                    String sendNumber = getSwitchBoardNumber();
                    server.createServerConnection(sendNumber);
                    server.getSMSC();
                }
            }
        } catch (Exception e) {

        }
    }

    private void cleanUp() { // st�nger connection
        displayen = null;
        if (server != null) {
            server = null;
        }
    }

    public void clockTryAgain() {
        Alert alertError = new Alert("Fel inmatning", "F�rs�k igen", null,
                                     AlertType.ERROR);
        alertError.setTimeout(Alert.FOREVER);
        textBoxLunch.setString("");
        textBoxTillbakaKlockan.setString("");
        textBoxUpptagen.setString("");

        displayen.setCurrent(alertError);
    }

    public String toString(String b) {

        String s = b;

        return s;
    }


    public void validateUser() throws //  METOD F�R VALIDERING AV USERNAME OCH PASSWORD
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        if (userName.getString().equals(Uname) &&
            userPassWord.getString().equals(passWord)) {
            displayen.setCurrent(getSettingsForm());

        } else {
            tryAgain();
        }
    }

    public void setSms(String setStatus) throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException { // METOD SOM SKICKAR STR�NGARARNA TILL ATT SKICKA WMA

        displayen.setCurrent(alertSender, listan);
        //setStatus = cU.testConvert(setStatus);
        String sendStatus = setStatus;
        String sendNumber = getSwitchBoardNumber();
        server.sendTextMessage(sendStatus, sendNumber);
        System.out.println(
                "har just skickat en str�ng till setSms - metoden som �r > " +
                sendStatus);
    }

//------------ COMMAND-ACTION STARTAR H�R MED TR�DARNA -------------------------

    public void run() {
        try {
            if (thCmd == exitListCommand) { // om det �r exitcommandot s� avsluta � st�ng igen connection och applikationen
                cleanUp();
                notifyDestroyed();
            } else if (thCmd == sendListCommand) { // eller om det �r sendCommand s� k�r appliktationen
                int valet;
                valet = listan.getSelectedIndex(); // OBS. ATT koden ser p� 'getSelectedIndex' f�r att se vilket ELEMENT I LISTAN SOM �R VALET

                if (valet == 0) { //Lunch Tillbaka kl

                    displayen.setCurrent(getTextBoxLunch());

                } else if (valet == 1) { //Semester Tillbaka Den

                    displayen.setCurrent(getTextBoxSemester());
                } else if (valet == 2) { //Tillbaka kl

                    displayen.setCurrent(getTextBoxTillbakaKlockan());

                } else if (valet == 3) { //Tillbaka Den

                    displayen.setCurrent(getTextBoxTillbakaDen());

                } else if (valet == 4) { // Ute

                    String attribut_05 = "05";

                    if (star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_05 +
                                               star; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms

                    }
                    if (!star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_05; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms
                    }
                }

                else if (valet == 5) { //Ring xxxx (max 17 siffror)

                    displayen.setCurrent(getTextBoxRing());

                } else if (valet == 6) { //Finns p� anknytning xxx 2 - max 4 siffror

                    displayen.setCurrent(getTextBoxAnknytning());

                } else if (valet == 7) { // Upptagen till kl

                    displayen.setCurrent(getTextBoxUpptagen());

                }

                else if (valet == 8) { //Sjuk

                    String attribut_09 = "09";

                    if (star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_09 +
                                               star; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms

                    }
                    if (!star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_09; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms

                    }

                }

                else if (valet == 9) { //Bortrest

                    // Ankytningsnummer + Attribut
                    String attribut_10 = "10";

                    if (star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_10 +
                                               star; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms

                    }
                    if (!star.equals("*")) {
                        this.ank = getAccessNumber();
                        String sendInputTime = ank + mellanslag + attribut_10; // l�gger ihop hela str�ngen som ska skickas

                        setSms(sendInputTime); // skickar in str�ngen skickar sms

                    }

                } else if (valet == 10) { //V�rd av Barn

                    // Ankytningsnummer + Attribut
                    String attribut_11 = "11";
                    this.ank = getAccessNumber();
                    String sendWAB = ank + mellanslag + attribut_11; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendWAB); // skickar in str�ngen skickar sms

                } else if (valet == 11) { //Ta bort h�nvisning

                    // Ankytningsnummer + Attribut
                    String attribut_12 = "99";
                    this.ank = getAccessNumber();
                    String sendGone = ank + mellanslag + attribut_12; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendGone); // skickar in str�ngen skickar sms

                } else if (valet == 12) { //Ta bort h�nvisning

                    displayen.setCurrent(listVal);

                }

            }

//----------- SE KOMMENTARER TILL H�GER OM KODEN F�R SE VILKET COMMANDO SOM H�R TILL VILKET

            else if (thCmd == goToListHelpFormCommand) { // Kommandot 'Hj�lp' h�r huvudf�nstret listan

                Displayable d = new HelpInfo();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(goToBackInfoHelpCommand);
                d.setCommandListener(this);

            } //lunchBackCommand

            else if (thCmd == goToBackInfoHelpCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                propertiesCommand = new Command("Egenskaper", Command.OK, 3);
                setDataStore();
                upDateDataStore();
                this.smsNumber = getSwitchBoardNumber();
                this.ank = getAccessNumber();
                Displayable d = new ServerNumber(ViewDateString, smsNumber,
                                                 ank, /*identy*/ star);
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(backFromServerNumberClassCommand);
                d.addCommand(goToListAboutFormCommand);
                d.addCommand(goToListHelpFormCommand);
                d.addCommand(propertiesCommand);
                d.setCommandListener(this);

            }

            else if (thCmd == lunchBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);
            } else if (thCmd == tillbakaKlBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);
            }

            else if (thCmd == lunchInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(lunchInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == tillbakaKlInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(tillbakaKlInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == upptagenInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(upptagenInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == ankInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(ankInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == ankInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxAnknytning());

            } else if (thCmd == ankBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);

            } else if (thCmd == tillbakaInfoCommand) { //tillbakaInfoCommand, tillbakaBackCommand, tillbakaInfoBackCommand, // kommandon f�r formen.,  Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(tillbakaInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == tillbakaInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxTillbakaDen());

            } else if (thCmd == tillbakaBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);

            }

            else if (thCmd == ringInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(ringInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == ringInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxRing());

            } else if (thCmd == semInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(semInfoBackCommand);
                d.setCommandListener(this);

            } else if (thCmd == semInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxSemester());

            } else if (thCmd == semBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);

            }

            else if (thCmd == ringBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);
            }

            else if (thCmd == upptagenInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxUpptagen());

            } else if (thCmd == upptagenBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(listan);

            } else if (thCmd == lunchInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxLunch());
            } else if (thCmd == tillbakaKlInfoBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxTillbakaKlockan());
            } else if (thCmd == upptagenInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getTextBoxUpptagen());
            }

            else if (thCmd == tillbakaSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputBackNumber = textBoxTillbakaDen.getString();
                String attribut_04 = "04";

                String setBackPart1 = inputBackNumber.substring(0, 2); // plockar ut tecken ur klockan
                String setBackPart2 = inputBackNumber.substring(2, 4); // plockar ut tecken ur klockan

                String setBackPart3 = setBackPart1;

                StringBuffer stringTimeStatus = new StringBuffer(setBackPart3); // skapar en buffert-string och s�tter ihop v�rde f�r att kunna j�mf�ra
                stringTimeStatus.append(setBackPart2);

                if (star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendBackTime = ank + mellanslag + attribut_04 +
                                          mellanslag +
                                          stringTimeStatus + star; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendBackTime); // skickar in str�ngen skickar sms

                }
                if (!star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendBackTime = ank + mellanslag + attribut_04 +
                                          mellanslag +
                                          stringTimeStatus; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendBackTime); // skickar in str�ngen skickar sms

                }

            } else if (thCmd == taBortVidKoppCommand) {

                displayen.setCurrent(listan);

            } else if (thCmd == vidKoppCommand) {
                int listValet;
                listValet = listVal.getSelectedIndex(); // OBS. ATT koden ser p� 'getSelectedIndex' f�r att se vilket ELEMENT I LISTAN SOM �R VALET

                if (listValet == 0) { //L�gg till vidarekoppling

                    star = "*";

                    openRecStore();
                    setStar(star);
                    closeRecStore();
                    upDateDataStore();

                    displayen.setCurrent(listan); // Spara i RMS och g� ut till listan igen

                } else if (listValet == 1) { //Ta bort vidarekoppling

                    star = "1";

                    openRecStore();
                    setStar(star);
                    closeRecStore();
                    upDateDataStore();

                    displayen.setCurrent(listan); // Spara i RMS och g� ut till listan igen

                }

            }

            else if (thCmd == semSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputSemNumber = textBoxSemester.getString();
                String attribut_02 = "02";

                String setSemPart1 = inputSemNumber.substring(0, 2); // plockar ut tecken ur klockan
                String setSemPart2 = inputSemNumber.substring(2, 4); // plockar ut tecken ur klockan

                String setSemPart3 = setSemPart1;

                StringBuffer stringTimeStatus = new StringBuffer(setSemPart3); // skapar en buffert-string och s�tter ihop v�rde f�r att kunna j�mf�ra
                stringTimeStatus.append(setSemPart2);

                if (star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendSemTime = ank + mellanslag + attribut_02 +
                                         mellanslag +
                                         stringTimeStatus + star; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendSemTime); // skickar in str�ngen skickar sms

                }
                if (!star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendSemTime = ank + mellanslag + attribut_02 +
                                         mellanslag +
                                         stringTimeStatus; // l�gger ihop hela str�ngen som ska skickas

                    setSms(sendSemTime); // skickar in str�ngen skickar sms

                }

            }

            else if (thCmd == ankSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputMobileNumber = textBoxAnknytning.getString();
                String attribut_07 = "07";

                if (star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendAnkNumber = ank + mellanslag + attribut_07 +
                                           mellanslag +
                                           inputMobileNumber + star;

                    setSms(sendAnkNumber); // skickar in str�ngen skickar sms

                }
                if (!star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendAnkNumber = ank + mellanslag + attribut_07 +
                                           mellanslag +
                                           inputMobileNumber;

                    setSms(sendAnkNumber); // skickar in str�ngen skickar sms

                }

            }

            else if (thCmd == ringSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputMobileNumber = textBoxRing.getString();
                String attribut_06 = "06";

                if (star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendRingNumber = ank + mellanslag + attribut_06 +
                                            mellanslag +
                                            inputMobileNumber + star;

                    setSms(sendRingNumber); // skickar in str�ngen skickar sms

                }
                if (!star.equals("*")) {
                    this.ank = getAccessNumber();
                    String sendRingNumber = ank + mellanslag + attribut_06 +
                                            mellanslag +
                                            inputMobileNumber;

                    setSms(sendRingNumber); // skickar in str�ngen skickar sms

                }

            }

            else if (thCmd == upptagenSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputMobileClock = textBoxUpptagen.getString();
                String attribut_08 = "08";

                setTime(inputMobileClock, attribut_08); // l�gg till kontroll inmatning av siffror

            }

            else if (thCmd == lunchSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputMobileClock = textBoxLunch.getString();
                String attribut_01 = "01";

                setTime(inputMobileClock, attribut_01); // l�gg till kontroll inmatning av siffror

            } else if (thCmd == tillbakaKlSendCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                String inputMobileClock = textBoxTillbakaKlockan.getString();
                String attribut_03 = "03";

                setTime(inputMobileClock, attribut_03); // l�gg till kontroll inmatning av siffror

            }

            else if (thCmd == editInfoSettingBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getEditSettingForm());
            }

            else if (thCmd == editSettingInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

                displayen.setCurrent(getEditSettingInfoForm());
            }

            else if (thCmd == helpFormBackCommand) { // Kommandot 'Tillbaka' h�r till hj�lp-formen

                displayen.setCurrent(listan);
            } else if (thCmd == aboutFormBackCommand) { // Kommandot 'Tillbaka' h�r till about-formen

                displayen.setCurrent(listan);
            } else if (thCmd == goToListAboutFormCommand) { // Kommandot 'Om Tv-Moble' h�r till huvudf�nstret listan

                Displayable d = new AboutUs();
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(goToBackInfoCommand);
                d.setCommandListener(this);

            } else if (thCmd == goToBackInfoCommand) { // Kommandot 'Tillbaka' h�r till about-formen

                propertiesCommand = new Command("Egenskaper", Command.OK, 3);
                setDataStore();
                upDateDataStore();
                this.smsNumber = getSwitchBoardNumber();
                this.ank = getAccessNumber();
                Displayable d = new ServerNumber(ViewDateString, smsNumber,
                                                 ank, /*identy*/ star);
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(backFromServerNumberClassCommand);
                d.addCommand(goToListAboutFormCommand);

                d.addCommand(goToListHelpFormCommand);
                d.addCommand(propertiesCommand);
                d.setCommandListener(this); //goToListHelpFormCommand goToListAboutFormCommand

            }

            else if (thCmd == goToListLoginLogInFormCommand) { // Kommandot 'egenskaper' h�r till huvudf�nstret listan

                propertiesCommand = new Command("Egenskaper", Command.OK, 3);
                setDataStore();
                upDateDataStore();
                this.smsNumber = getSwitchBoardNumber();
                this.ank = getAccessNumber();
                Displayable d = new ServerNumber(ViewDateString, smsNumber,
                                                 ank, /*identy*/ star);
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(backFromServerNumberClassCommand);
                d.addCommand(goToListAboutFormCommand);

                d.addCommand(goToListHelpFormCommand);
                d.addCommand(propertiesCommand);
                d.setCommandListener(this); //goToListHelpFormCommand goToListAboutFormCommand

            } else if (thCmd == propertiesCommand) { // Kommandot 'Redigera' h�r till setting-Form

                displayen.setCurrent(getEditSettingForm());
            } else if (thCmd == backFromServerNumberClassCommand) { // Kommandot 'Tillbaka' h�r till Admin-form

                displayen.setCurrent(listan);

            }

            else if (thCmd == loggInCommand) { // Kommandot 'Logga In' h�r till Admin-form

                validateUser();
            } else if (thCmd == logInbackCommand) { // Kommandot 'Tillbaka' h�r till Admin-form

                displayen.setCurrent(listan);

            } else if (thCmd == settingsCancelCommand) { // Kommandot 'Logga Ut' h�r till setting-Form

                displayen.setCurrent(listan);
            } else if (thCmd == settingsChangeCommand) { // Kommandot 'Redigera' h�r till setting-Form

                displayen.setCurrent(getEditSettingForm());
            } else if (thCmd == editSettingCancelCommand) { // Kommandot 'Logga Ut' h�r till editSetting-Form

                displayen.setCurrent(listan);
            } else if (thCmd == editSettingSaveCommand) { // Kommandot 'Spara' h�r till editSetting-Form

                openRecStore();
                //setName();
                setAccessNumber();
                setSwitchBoardNumber();
                closeRecStore();
                upDateDataStore();
                displayen.setCurrent(alertEditSettings, listan);

            } else if (thCmd == editSettingBackCommand) { // Kommandot 'Tillbaka' h�r till editSetting-Form

                propertiesCommand = new Command("Egenskaper", Command.OK, 3);
                setDataStore();
                upDateDataStore();
                this.smsNumber = getSwitchBoardNumber();
                this.ank = getAccessNumber();
                Displayable d = new ServerNumber(ViewDateString, smsNumber,
                                                 ank, /*identy*/ star);
                Display.getDisplay(this).setCurrent(d);
                d.addCommand(backFromServerNumberClassCommand);
                d.addCommand(goToListAboutFormCommand);
                d.addCommand(goToListHelpFormCommand);
                d.addCommand(propertiesCommand);
                d.setCommandListener(this); //goToListHelpFormCommand goToListAboutFormCommand

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WMAMIDlet.run Exception " + e);
        }
    }

//------------ Check-input-time ------------------------------------------------

    public void setTime(String sendInput, String attributs) throws
            NumberFormatException, InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String attribut = attributs;
        String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumv�rde, skickar och g�r om till en string av java.lang.string-typ

        String setMobileClockCharacterPart1 = mobileClock.substring(11, 13); // plockar ut tecken ur klockan
        String setMobileClockCharacterPart2 = mobileClock.substring(14, 16); // plockar ut tecken ur klockan

        StringBuffer checkMobileNumber = new StringBuffer(
                setMobileClockCharacterPart1); // skapar en bufferstring och s�tter ihop v�rde f�r att kunna j�mf�ra
        checkMobileNumber.append(setMobileClockCharacterPart2);

        String ConvertcheckMobileNumber = checkMobileNumber.toString(); // skickar buffestringen till toString() f�r att skapa ett string-objekt

        Integer convertMobilePartNumber1 = new Integer(0); // G�r om str�ngar till integer
        int mobileNumberPart1 = convertMobilePartNumber1.parseInt(
                setMobileClockCharacterPart1);

        Integer convertMobilePartNumber2 = new Integer(0); // G�r om str�ngar till integer
        int mobileNumberPart2 = convertMobilePartNumber2.parseInt(
                setMobileClockCharacterPart2);

        int summaMobilePartNumber1 = mobileNumberPart1 * 60; // utf�r matematisk tidr�kning f�r tidenheter
        int summaMobilePartNumber2 = mobileNumberPart2;
        int totalSummaMobilePartNumber = summaMobilePartNumber1 +
                                         summaMobilePartNumber2;

        //------------------------------------------------------------------------------------------------------------------------------------

        String inputMobileClock = sendInput; // tilldelar inputMobileclock den inmatade str�ngen

        String inputCheckMobileClock = inputMobileClock; // skapar en str�ng f�r att typ�ndra till int och j�mf�ra med checkMobileNumber

        Integer checkMobileNumberToInt = new Integer(0); // konverterar till typen > integer
        int MobileTimeClock = checkMobileNumberToInt.parseInt(
                ConvertcheckMobileNumber);

        Integer checkInputMobileNumberToInt = new Integer(0); // konverterar till typen > integer
        int MobileInputTimeClock = checkInputMobileNumberToInt.parseInt(
                inputCheckMobileClock);

        System.out.println("Skriver ut MobileInputTimeClock > " +
                           MobileInputTimeClock +
                           "\n Skriver ut MobileTimeClock > " + MobileTimeClock);

        //----------------- LOGISKA UTTRYCK F�R INMATNINGEN ----------------------------------------------------------------------------------

        String checkNumberType = inputMobileClock;

        String TypeOfNumberCheck = checkNumberType.substring(0, 1); // plockar ut tecken ut str�ngen
        String TypeOfNumberCheck2 = checkNumberType.substring(0, 2); // plockar ut tecken ut str�ngen
        String TypeOfNumberCheck3 = checkNumberType.substring(2, 3); // plockar ut tecken ut str�ngen

        Integer checkInputMobilePartNumber1 = new Integer(0);
        int convertedInputNumberPart1 = checkInputMobilePartNumber1.parseInt(
                TypeOfNumberCheck);

        Integer checkInputMobilePartNumber2 = new Integer(0);
        int convertedInputNumberPart2 = checkInputMobilePartNumber2.parseInt(
                TypeOfNumberCheck2);

        Integer checkInputMobilePartNumber3 = new Integer(0);
        int convertedInputNumberPart3 = checkInputMobilePartNumber3.parseInt(
                TypeOfNumberCheck3);

        if (convertedInputNumberPart1 >= 3 || convertedInputNumberPart2 >= 24 ||
            convertedInputNumberPart3 >= 6
            || MobileTimeClock == MobileInputTimeClock ||
            MobileTimeClock > MobileInputTimeClock) {

            clockTryAgain();

        } else {

            String setTimeStatusPart1 = inputMobileClock.substring(0, 2); // plockar ut tecken ur klockan
            String setTimeStatusPart2 = inputMobileClock.substring(2, 4); // plockar ut tecken ur klockan

            String setTimeStatusPart3 = setTimeStatusPart1;

            StringBuffer stringTimeStatus = new StringBuffer(setTimeStatusPart3); // skapar en buffert-string och s�tter ihop v�rde f�r att kunna j�mf�ra
            stringTimeStatus.append(setTimeStatusPart2);

            if (star.equals("*")) {
                this.ank = getAccessNumber();
                String sendInputTime = ank + mellanslag + attribut + mellanslag +
                                       stringTimeStatus + star; // l�gger ihop hela str�ngen som ska skickas

                setSms(sendInputTime); // skickar in str�ngen skickar sms

                System.out.println(
                        "visar den totala summan 'av typen String' som skickas till Mobile-PS > " +
                        sendInputTime);

            }
            if (!star.equals("*")) {
                this.ank = getAccessNumber();
                String sendInputTime = ank + mellanslag + attribut + mellanslag +
                                       stringTimeStatus; // l�gger ihop hela str�ngen som ska skickas

                setSms(sendInputTime); // skickar in str�ngen skickar sms

                System.out.println(
                        "visar den totala summan 'av typen String' som skickas till Mobile-PS > " +
                        sendInputTime);

            }

        }
    }

    //------------ D A T A - B A S - R M S -----------------------------------------

    public Form getEditSettingForm() { // METODEN RETURNERAR FORMEN F�R EDITSETTINGS I EGENSKAPER

        editSettingForm.deleteAll();
        openRecStore();
        accessNumbers.setString(accessNumber);
        editSettingForm.append(accessNumbers);
        editSwitchBoardNumber.setString(switchBoardNumber);
        editSettingForm.append(editSwitchBoardNumber);
        closeRecStore();

        return editSettingForm;
    }

    // --- SET-metoder ------



    public void setDateNumber() {

        try {
            recStore.setRecord(3, dateNumber.getString().getBytes(), 0,
                               dateNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setAccessNumber() {

        try {
            recStore.setRecord(4, accessNumbers.getString().getBytes(), 0,
                               accessNumbers.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setSwitchBoardNumber() {
        try {
            recStore.setRecord(5, editSwitchBoardNumber.getString().getBytes(),
                               0,
                               editSwitchBoardNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setStar(String p) {

        String setNewStar = p;

        try {
            recStore.setRecord(9, setNewStar.getBytes(), 0,
                               setNewStar.length());
            System.out.println(
                    "�r i validateUser-metoden skriver ut instansen Star som �r >> " +
                    setNewStar);
        } catch (Exception e) {
            // ALERT
        }
    }


    public void setTWO() {
        try {

            openRecStore();
            String appt = "2";
            byte bytes[] = appt.getBytes();
            recStore.addRecord(bytes, 0, bytes.length);

            closeRecStore();
            upDateDataStore();
            startApp();

        } catch (Exception e) {
            // ALERT
        }
    }


    // ---- GET-metoder ---------

    public String getYear() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte a[] = recStore.getRecord(1);
        setYear = new String(a, 0, a.length);

        closeRecStore();

        return setYear;

    }

    public String getMounth() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte b[] = recStore.getRecord(2);
        setMounth = new String(b, 0, b.length);

        closeRecStore();

        return setMounth;

    }

    public String getDate() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte c[] = recStore.getRecord(3);
        setDate = new String(c, 0, c.length);

        closeRecStore();

        return setDate;

    }

    public String getAccessNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte d[] = recStore.getRecord(4);
        accessNumber = new String(d, 0, d.length);

        closeRecStore();

        return accessNumber;

    }


    public String getSwitchBoardNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte e[] = recStore.getRecord(5);
        switchBoardNumber = new String(e, 0, e.length);

        closeRecStore();

        return switchBoardNumber;

    }

    public String getThisYearBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte f[] = recStore.getRecord(6);
        setyearBack = new String(f, 0, f.length);

        closeRecStore();

        return setyearBack;

    }

    public String getThisMounthBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte g[] = recStore.getRecord(7);
        setmounthBack = new String(g, 0, g.length);

        closeRecStore();

        return setmounthBack;

    }

    public String getThisDayBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte h[] = recStore.getRecord(8);
        setdayBack = new String(h, 0, h.length);

        closeRecStore();

        return setdayBack;

    }


    public void getTWO() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();
        readRecords();
        readRecordsUpdate();

        try {
            byte j[] = recStore.getRecord(10);
            getTWO = new String(j, 0, j.length);
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            this.dateString = getTWO;
        } catch (Exception ex1) {
        }

        System.out.println("h����������rrrrrrrr >>> getTWO >> " + getTWO);
        closeRecStore();

    }

    public void readRecordsUpdate() {
        try {
            System.out.println("Number of records: " + recStore.getNumRecords());

            if (recStore.getNumRecords() > 0) {
                RecordEnumeration re = recStore.enumerateRecords(null, null, false);
                while (re.hasNextElement()) {
                    String str = new String(re.nextRecord());
                    System.out.println("Record: " + str);
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void readRecords() {
        try {
            // Intentionally small to test code below
            byte[] recData = new byte[5];
            int len;

            for (int i = 1; i <= recStore.getNumRecords(); i++) {
                // Allocate more storage if necessary
                if (recStore.getRecordSize(i) > recData.length) {
                    recData = new byte[recStore.getRecordSize(i)];
                }

                len = recStore.getRecord(i, recData, 0);
                if (Settings.debug) {
                    System.out.println("Record ID#" + i + ": " +
                                       new String(recData, 0, len));
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void writeRecord(String str) {
        byte[] rec = str.getBytes();

        try {
            System.out.println("sparar ");
            recStore.addRecord(rec, 0, rec.length);
            System.out.println("Writing record: " + str);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public void openRecStore() {
        try {
            System.out.println("�ppnar databasen");
            // The second parameter indicates that the record store
            // should be created if it does not exist
            recStore = RecordStore.openRecordStore(REC_STORE, true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void closeRecStore() {
        try {
            recStore.closeRecordStore();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void setDataStore() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreNotOpenException,
            RecordStoreException {

        openRecStore();
        readRecords();
        readRecordsUpdate();

        if (recStore.getNumRecords() == 0) { // om inneh�llet i databasen �r '0' s� spara de tre f�rsta elementen i databasen.

            writeRecord(setYear);
            writeRecord(setMounth);
            writeRecord(setDate);
            writeRecord("0");
            writeRecord("+46");
            writeRecord(setyearBack);
            writeRecord(setmounthBack);
            writeRecord(setdayBack);
            writeRecord(star);

        }

        // s�tter nummer i f�nstret under inst�llningar...

        byte d[] = recStore.getRecord(4);
        accessNumber = new String(d, 0, d.length);

        byte e[] = recStore.getRecord(5);
        switchBoardNumber = new String(e, 0, e.length);

        closeRecStore();
    }

    // Om n�got inputf�nster(post) i databasen �r tom s�tt tillbaka v�rdet...
    public void upDateDataStore() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();
        String setBackUserDateRecord = setDate;
        String setBackAccessNumberRecord = accessNumber;
        String setBackSwitchBoardNumberRecord = switchBoardNumber;

        if (recStore.getRecord(1) == null && recStore.getRecord(4) == null &&
            recStore.getRecord(5) == null) {

            recStore.setRecord(1, setBackUserDateRecord.getBytes(), 0,
                               setBackUserDateRecord.length());
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
        } else if (recStore.getRecord(1) == null && recStore.getRecord(4) == null) {

            recStore.setRecord(1, setBackUserDateRecord.getBytes(), 0,
                               setBackUserDateRecord.length());
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
        } else if (recStore.getRecord(1) == null && recStore.getRecord(5) == null) {

            recStore.setRecord(1, setBackUserDateRecord.getBytes(), 0,
                               setBackUserDateRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
        } else if (recStore.getRecord(1) == null) {

            recStore.setRecord(1, setBackUserDateRecord.getBytes(), 0,
                               setBackUserDateRecord.length());
        } else if (recStore.getRecord(4) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
        } else if (recStore.getRecord(5) == null) {

            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
        }

        closeRecStore();
    }


// ------------------- D A T U M -----------------------------------------------

    public void controllString() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        String readRecord;

        getTWO();

        readRecord = dateString;

        String viewRecord = readRecord;

        try {
            if (viewRecord.equals("2")) {

                notifyDestroyed();
            }
        } catch (Exception ex) {
        }
        System.out.println("V�RDET PLATS 9 DB >> " + viewRecord);
    }

    public void controllDate() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        try {
            this.DBdate = getDate();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            this.DBmounth = getMounth();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            this.DByear = getYear();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.DBdateBack = getThisDayBack();
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }
        try {
            this.DBmounthBack = getThisMounthBack();
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }
        try {
            this.DByearBack = getThisYearBack();
        } catch (RecordStoreNotOpenException ex5) {
        } catch (InvalidRecordIDException ex5) {
        } catch (RecordStoreException ex5) {
        }

        String useDBdate = null;
        try {
            useDBdate = DBdate.trim();
        } catch (Exception ex6) {
        }
        String useDBmounth = null;
        try {
            useDBmounth = DBmounth.trim();
        } catch (Exception ex7) {
        }
        String useDByear = null;
        try {
            useDByear = DByear.trim();
        } catch (Exception ex8) {
        }

        String useDBdateBack = null;
        try {
            useDBdateBack = DBdateBack.trim();
        } catch (Exception ex9) {
        }
        String useDBmounthBack = null;
        try {
            useDBmounthBack = DBmounthBack.trim();
        } catch (Exception ex10) {
        }
        String useDByearBack = null;
        try {
            useDByearBack = DByearBack.trim();
        } catch (Exception ex11) {
        }

        System.out.println("Skriver ut datum om 30 dagar >>> " + useDBdate);
        System.out.println("Skriver ut m�nad om 30 dagar >>> " + useDBmounth);
        System.out.println("Skriver ut �ret om 30 dagar >>> " + useDByear);

        System.out.println("Skriver ut Kontroll datum >>> " + useDBdateBack);
        System.out.println("Skriver ut Kontroll m�nad >>> " + useDBmounthBack);
        System.out.println("Skriver ut Kontroll �r >>> " + useDByearBack);

        String toDayDate = checkDay().trim();
        String toDayMounth = checkMounth().trim();

        System.out.println("Skriver ut DAGENS DATUM >>> " + toDayDate);
        System.out.println("Skriver ut �RETS M�NAD >>> " + toDayMounth);

        Integer controllDBdateBack = new Integer(0); // G�r om str�ngar till integer
        Integer controllDBmonthBack = new Integer(0); // G�r om str�ngar till integer
        Integer controllDByearBack = new Integer(0); // G�r om str�ngar till integer

        int INTDBdateBack = 0;
        try {
            INTDBdateBack = controllDBdateBack.parseInt(useDBdateBack);
        } catch (NumberFormatException ex12) {
        }
        int INTDBmounthBack = 0;
        try {
            INTDBmounthBack = controllDBmonthBack.parseInt(DBmounthBack);
        } catch (NumberFormatException ex13) {
        }
        int INTDByearBack = 0;
        try {
            INTDByearBack = controllDByearBack.parseInt(DByearBack);
        } catch (NumberFormatException ex14) {
        }

        Integer controllDBdate = new Integer(0); // G�r om str�ngar till integer
        Integer controllDBmonth = new Integer(0); // G�r om str�ngar till integer
        Integer controllDByear = new Integer(0); // G�r om str�ngar till integer

        Integer controllToDayDBdate = new Integer(0); // G�r om str�ngar till integer
        Integer controllToDayDBmounth = new Integer(0); // G�r om str�ngar till integer

        int INTDBdate = 0;
        try {
            INTDBdate = controllDBdate.parseInt(useDBdate);
        } catch (NumberFormatException ex15) {
        }
        int INTDBmounth = 0;
        try {
            INTDBmounth = controllDBmonth.parseInt(DBmounth);
        } catch (NumberFormatException ex16) {
        }
        int INTDByear = 0;
        try {
            INTDByear = controllDByear.parseInt(DByear);
        } catch (NumberFormatException ex17) {
        }

        int INTdateToDay = 0;
        try {
            INTdateToDay = controllToDayDBdate.parseInt(toDayDate);
        } catch (NumberFormatException ex18) {
        }
        int INTmounthToDay = 0;
        try {
            INTmounthToDay = controllToDayDBmounth.parseInt(toDayMounth);
        } catch (NumberFormatException ex19) {
        }

        if (INTDBdate <= INTdateToDay && INTDBmounth <= INTmounthToDay &&
            INTDByear == checkYear) {

            System.out.println("SANN SANN SANN SANN SANN ");

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }
        if (INTmounthToDay == 0) { // Om INTmounthToDay har v�rdet '0' som �r januari

            INTDBmounthBack = 0; // D� inneh�ller installations-m�naden samma v�rde som nu-m�naden.

        }
        if (INTDBmounthBack > INTmounthToDay) { // Om installations-m�naden �r st�rre �n 'dagens' m�nad som �r satt i mobilen s� st�ng...

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }
        if (INTDBmounthBack > INTmounthToDay && INTDByearBack < checkYear) { // Om installations-m�naden �r st�rre �n 'dagens' m�nad som �r satt i mobilen s� st�ng...

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }
        if (INTDByearBack > checkYear) { // Om installations-�ret �r st�rre �n �ret som �r satt i mobilen. >> g�r bak�t i tiden...

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }
        if (INTDBdateBack > INTdateToDay && INTDBmounthBack > INTmounthToDay &&
            INTDByearBack > checkYear) {

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }
        if (INTDBmounthBack > INTmounthToDay && INTDByearBack > checkYear) {

            setTWO(); // Om m�nad och datum �r sann skriv in "2" i databasen plats 6...

        }

    }

    public void setDBDate() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        countDay();

        System.out.println("Om 30 dagar �r det den >> " + dayAfter +
                           ", och m�nad >> " + monthAfter + " det �r �r >> " +
                           yearAfter);

        String convertDayAfter = Integer.toString(dayAfter); // konvertera int till string...
        String convertMounthAfter = Integer.toString(monthAfter);
        String convertYearAfter = Integer.toString(yearAfter);

        this.setDate = convertDayAfter;
        this.setMounth = convertMounthAfter;
        this.setYear = convertYearAfter;

    }

    public void setDBDateBack() {

        countThisDay();

        System.out.println("Kontrollerar dagens dautm >> " + dayBack +
                           ", och m�nad >> " + mounthBack + " det �r �r >> " +
                           yearBack);

        String convertDayBack = Integer.toString(dayBack); // konvertera int till string...
        String convertMounthBack = Integer.toString(mounthBack);
        String convertYearBack = Integer.toString(yearBack);

        this.setdayBack = convertDayBack;
        this.setmounthBack = convertMounthBack;
        this.setyearBack = convertYearBack;

    }

    public void countThisDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum �r den >> " + day +
                           ", �rets m�nad �r nummer >> " + month +
                           " det �r �r >> " + year);

        this.dayBack = day;
        this.mounthBack = month;
        this.yearBack = year;

    }

    public void countDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum �r den >> " + day +
                           ", �rets m�nad �r nummer >> " + month +
                           " det �r �r >> " + year);
        this.checkYear = year;

        // R�knar fram 30 dagar fram�t vilket datum �r osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += antalDagar * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);

        // Now get the adjusted date back
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);

        this.dayAfter = day;
        this.monthAfter = month;
        this.yearAfter = year;

    }

    private String regFromTextFile() { // L�ser textfilen tmp.txt
        InputStream is = getClass().getResourceAsStream("tmp.txt");
        try {
            StringBuffer sb = new StringBuffer();
            int chr, i = 0;
            // Read until the end of the stream
            while ((chr = is.read()) != -1) {
                sb.append((char) chr);
            }

            return sb.toString();
        } catch (Exception e) {
            System.out.println("Unable to create stream");
        }
        return null;
    }

    public String setViewDateString() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        //ViewDateString

        String e1 = getDate();
        String e2 = setMounth();
        String e3 = getYear();

        ViewDateString = e1 + " " + e2 + " " + e3;

        return ViewDateString;

    }

    public String setMounth() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        setViewMounth = getMounth();

        if (setViewMounth.equals("0")) {

            this.setViewMounth = "Januari";
        }
        if (setViewMounth.equals("1")) {

            this.setViewMounth = "Februari";
        }
        if (setViewMounth.equals("2")) {

            this.setViewMounth = "Mars";
        }
        if (setViewMounth.equals("3")) {

            this.setViewMounth = "April";
        }
        if (setViewMounth.equals("4")) {

            this.setViewMounth = "Maj";
        }
        if (setViewMounth.equals("5")) {

            this.setViewMounth = "Juni";
        }
        if (setViewMounth.equals("6")) {

            this.setViewMounth = "Juli";
        }
        if (setViewMounth.equals("7")) {

            this.setViewMounth = "Augusti";
        }
        if (setViewMounth.equals("8")) {

            this.setViewMounth = "September";
        }
        if (setViewMounth.equals("9")) {

            this.setViewMounth = "Oktober";
        }
        if (setViewMounth.equals("10")) {

            this.setViewMounth = "November";
        }
        if (setViewMounth.equals("11")) {

            this.setViewMounth = "December";
        }

        String viewMounth = setViewMounth;

        return viewMounth;
    }

    public String checkDay() {

        String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumv�rde, skickar och g�r om till en string av java.lang.string-typ

        String checkDayString = mobileClock.substring(8, 10); // plockar ut 'datum' tecken ur klockan

        if (checkDayString.equals("01")) {

            checkDayString = "1";

        } else if (checkDayString.equals("02")) {

            checkDayString = "2";

        } else if (checkDayString.equals("03")) {

            checkDayString = "3";

        } else if (checkDayString.equals("04")) {

            checkDayString = "4";

        } else if (checkDayString.equals("05")) {

            checkDayString = "5";

        } else if (checkDayString.equals("06")) {

            checkDayString = "6";

        } else if (checkDayString.equals("07")) {

            checkDayString = "7";

        } else if (checkDayString.equals("08")) {

            checkDayString = "8";

        } else if (checkDayString.equals("09")) {

            checkDayString = "9";

        }

        String useStringDate = checkDayString;

        return useStringDate;

    }

    public String checkMounth() {

        String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumv�rde, skickar och g�r om till en string av java.lang.string-typ

        String checkMounthString = mobileClock.substring(4, 7); // plockar ut 'M�nad' tecken ur klockan

        if (checkMounthString.equals("Jan")) {

            checkMounthString = "0";

        } else if (checkMounthString.equals("Feb")) {

            checkMounthString = "1";

        } else if (checkMounthString.equals("Mar")) {

            checkMounthString = "2";

        } else if (checkMounthString.equals("Apr")) {

            checkMounthString = "3";

        } else if (checkMounthString.equals("May")) {

            checkMounthString = "4";

        } else if (checkMounthString.equals("Jun")) {

            checkMounthString = "5";

        } else if (checkMounthString.equals("Jul")) {

            checkMounthString = "6";

        } else if (checkMounthString.equals("Aug")) {

            checkMounthString = "7";

        } else if (checkMounthString.equals("Sep")) {

            checkMounthString = "8";

        } else if (checkMounthString.equals("Oct")) {

            checkMounthString = "9";

        } else if (checkMounthString.equals("Nov")) {

            checkMounthString = "10";

        } else if (checkMounthString.equals("Dec")) {

            checkMounthString = "11";

        }

        String useStringMounth = checkMounthString;

        return useStringMounth;

    }

    public void destroyApp(boolean unconditional) {} // avsluta programmet

    public void pauseApp() {} // pausa programmet

    private void DateAndTimeItem() throws Exception { // METOD F�R ATT BILDER, DATE-FORM TEX....
        imageItem1 = new ImageItem("", null, ImageItem.LAYOUT_CENTER, "");
        imageItem1.setLabel("");
        imageItem1.setAltText("");
    }
}
