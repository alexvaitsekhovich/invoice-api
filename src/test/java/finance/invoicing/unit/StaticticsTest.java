package finance.invoicing.unit;

import finance.invoicing.helper.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
public class StaticticsTest {

    @Test
    public void testPrediction1() {
        List<Double> data = new ArrayList<>();
        data.add(100.0);
        data.add(100.0);
        data.add(100.0);
        data.add(100.0);

        double prediction = Statistics.calculatePrediction(data, 1.0);

        assertEquals(prediction, 100.0, 0);
    }

    @Test
    public void testPrediction2() {
        List<Double> data = new ArrayList<>();
        data.add(100.0);
        data.add(100.0);
        data.add(100.0);
        data.add(100.0);

        double prediction = Statistics.calculatePrediction(data, 0.2);

        assertEquals(prediction, 100.0, 0);
    }

    @Test
    public void testPrediction3() {
        List<Double> data = new ArrayList<>();
        data.add(100.0);
        data.add(110.0);
        data.add(120.0);
        data.add(130.0);

        double prediction = Statistics.calculatePrediction(data, 1.0);

        assertEquals(prediction, 130.0, 0);
    }

    @Test
    public void testPrediction4() {
        List<Double> data = new ArrayList<>();
        data.add(100.0);
        data.add(110.0);
        data.add(120.0);
        data.add(130.0);

        double prediction = Statistics.calculatePrediction(data, 0.2);

        assertEquals(prediction, 110.48, 0.01);
    }

    @Test
    public void testSeasonalPrediction1() {
        Map<Integer, Double> monthValues = new HashMap<>();
        monthValues.put(2018, 100.0);
        monthValues.put(2016, 100.0);
        monthValues.put(2015, 100.0);
        monthValues.put(2017, 100.0);

        Map<Integer, Double> avgValues = new HashMap<>();
        avgValues.put(2015, 100.0);
        avgValues.put(2016, 100.0);
        avgValues.put(2017, 100.0);
        avgValues.put(2018, 100.0);

        double nonSeasonalPrediction = 50.0;

        double seasonalPrediction = Statistics.calculateSeasonalPrediction(nonSeasonalPrediction,
                monthValues, avgValues);

        assertEquals(seasonalPrediction, 50.0, 0.01);
    }

    @Test
    public void testSeasonalPrediction2() {
        Map<Integer, Double> monthValues = new HashMap<>();
        monthValues.put(2016, 110.0);
        monthValues.put(2015, 100.0);
        monthValues.put(2018, 130.0);
        monthValues.put(2017, 120.0);

        Map<Integer, Double> avgValues = new HashMap<>();
        avgValues.put(2016, 100.0);
        avgValues.put(2018, 100.0);
        avgValues.put(2015, 100.0);
        avgValues.put(2017, 100.0);

        double nonSeasonalPrediction = 50.0;

        double seasonalPrediction = Statistics.calculateSeasonalPrediction(nonSeasonalPrediction,
                monthValues, avgValues);

        assertEquals(seasonalPrediction, 60.62, 0.01);
    }

    @Test
    public void testSeasonalPrediction3() {
        Map<Integer, Double> monthValues = new HashMap<>();
        monthValues.put(2016, 150.0);

        Map<Integer, Double> avgValues = new HashMap<>();
        avgValues.put(2016, 100.0);
        avgValues.put(2018, 100.0);
        avgValues.put(2015, 100.0);
        avgValues.put(2017, 100.0);

        double nonSeasonalPrediction = 50.0;

        double seasonalPrediction = Statistics.calculateSeasonalPrediction(nonSeasonalPrediction,
                monthValues, avgValues);

        assertEquals(seasonalPrediction, 75.0, 0.01);
    }
}
