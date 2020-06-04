package call;

import call.person.Participant;

public class CallManagerApplication {
    public static void main(String[] args) {
        print("Application started...");

//        call.person.CallAdministrator callAdmin = call.person.CallAdministrator.getInstance();
//        callAdmin.initAdmin("Elio", "Guadagnini");
//
//        callAdmin.addParticipant(new call.person.Participant("Mario", "Rossi"));
//
//        call.person.Participant toBeRemoved = new call.person.Participant("Mario", "Rossi");
//        callAdmin.addParticipant(toBeRemoved);
//        callAdmin.removeParticipant(toBeRemoved);
//
//        callAdmin.allParticipants();
//

        try {
            Call callObj;
            callObj = handleArgs(args);

            if (callObj == null) {
                callObj = new Call("Elio", "Guadagnini");
                callObj.addParticipant(new Participant("2","Mario", "Rossi"));

                Participant toBeRemoved = new Participant("3","Maria", "Rossi");
                callObj.addParticipant(toBeRemoved);
                callObj.removeParticipant(toBeRemoved);
            }
            callObj.performCallOperations();

            callObj.closeCall();
        } catch(Exception e) {
            printError("Unexpected error.");
            e.printStackTrace();
        }  finally { print("Execution ended up."); }
    }

    public static Call handleArgs(String[] args) {
        if (args.length > 0 && args.length % 2 == 0) {
            Call callObj = new Call(args[0], args[1]);
            if (args.length >= 2) {
                for (int i=2; i<args.length-1; i+=2) {
                    callObj.addParticipant(new Participant(String.valueOf(i), args[i], args[i + 1]));
                }
            }
            return callObj;
        }
        printUsage();
        return null;
    }

    public static void printError(String message) {
        print(message);
        printUsage();
    }

    public static void printUsage() {
        print("Usage: you should pass arguments as pairs of (Name, Surname), where the first represents the call admin.");
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
