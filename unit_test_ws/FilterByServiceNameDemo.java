import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FilterByServiceNameDemo{

    public static void main(String[] agrs){
        List<String> rawResults = new ArrayList<String>();
        List<String> results = new ArrayList<String>();

        rawResults.add("20191109T215952_1000002aa_getRandomNumber.log");
        rawResults.add("20191109T215956_1000002_getRandomNumber.log");
        rawResults.add("20191109T220000_1000002ab_getRandomNumber.log");
        rawResults.add("20191109T220010_IBHKE1000002_getRandomNumber.log");
        rawResults.add("20191109T220021_IBHKE100077700123456_A002026K_login2FASMS.log");
        rawResults.add("20191109T220032_IBHKE100077700123456_A002026K_login1FA.log");
        rawResults.add("20191109T220036_IBHKE100077700123456_A002026K_login1FA.log");
        rawResults.add("20191109T220044_IBHKE100077700123456_A002026KKK_login1FA.log");
        rawResults.add("20191109T220349_-9084719904665457103_ping.log");

        results = filterByServiceName(rawResults,"login2FASMS");
        System.out.println(results);
    }

    public static List<String> filterByServiceName(List<String> rawResults,String serviceName){
        List<String> result;

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            System.out.println(Arrays.toString(fileNameArr));
            if (fileNameArr[fileNameArr.length-1].split("\\.")[0].equals(serviceName)){
                System.out.println(true);
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }
}