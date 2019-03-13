package finance.invoicing.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Statistics {

    /**
     *
     * @param data - list of data for calculation
     * @param smoothing - weight of the current element, (1-smoothing) is the weight of accumulated value
     * @return prediction
     */
    public static double calculatePrediction(List<Double> data, double smoothing) {
        double prediction = data.get(0);

        for(int ind = 1; ind < data.size(); ind++){
            prediction = data.get(ind) * smoothing + prediction * (1 - smoothing);
        }

        return prediction;
    }

    public static double calculateSeasonalPrediction(double nonSeasonalValue,
                                                     Map<Integer, Double> monthValues,
                                                     Map<Integer, Double> avgValues) {
        // calculate relation of the month to the average of the year for every year
        Map<Integer, Double> monthToYearAvg = monthValues.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue() / avgValues.get(el.getKey()) ));

        // sort the average. TreeMap is always sorted by the natural key order
        Map<Integer, Double> monthToYearAvgSorted = new TreeMap<>(monthToYearAvg);

        // calculate the predicted deviation of the month, using 0.5 smoothing, i.e.
        // the recent development contributes 50% to the calculation and the accumulated values - another 50%
        double predictedDeviation = Statistics.calculatePrediction(new ArrayList<>(monthToYearAvgSorted.values()), 0.5);

        return nonSeasonalValue * predictedDeviation;
    }
}
