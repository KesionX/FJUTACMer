package tool;

/**
 * Created by Administrator on 2016/10/4.
 */
public class ProblemStatusDec {
    private static ProblemStatusDec problemStatusDec = null;


    public static ProblemStatusDec getProblemStatusDec(){
        if(problemStatusDec==null){
            problemStatusDec = new ProblemStatusDec();
        }
        return problemStatusDec;
    }

    public static String getLangString(int lang){
        if(lang==0) return "c++";
        if (lang==1) return "c";
        return "java";
    }
    public static String getResultString(int s){

        if(s==1) return "Accepted";
        if(s==3) return "Compilation Error";
        if(s==9) return "System Error";
        if(s==6) return "Memory Limit Exceeded";
        if(s==7) return"Output Limit Exceeded";
        if(s==8) return "Presentation Error";
        if(s==0) return "Pendding...";
        if(s==12) return "Judging...";
        if(s==4) return "Runtime Error";
        if(s==10) return "Running...";
        if(s==5) return "Time Limit Exceeded";
        if(s==2) return "Wrong Answer";
        if(s==11) return "Submit Error";
        return "System Error";
    }
}
