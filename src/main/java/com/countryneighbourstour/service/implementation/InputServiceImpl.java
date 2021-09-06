package com.countryneighbourstour.service.implementation;

import com.countryneighbourstour.model.dto.InputDto;
import com.countryneighbourstour.model.entity.Country;
import com.countryneighbourstour.model.entity.Result;
import com.countryneighbourstour.model.entity.TotalBudget;
import com.countryneighbourstour.repository.CountryRepository;
import com.countryneighbourstour.service.CountryService;
import com.countryneighbourstour.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class InputServiceImpl implements InputService {

    private final CountryRepository countryRepository;
    private final CountryService countryService;

    @Autowired
    public InputServiceImpl(CountryRepository countryRepository, CountryService countryService) {
        this.countryRepository = countryRepository;
        this.countryService = countryService;
    }

    @Override
    public Result getData(InputDto inputDto) throws IOException {
        Country startingCountry = this.countryRepository.findByCountryName(inputDto.getCountryName());
        ArrayList<String> neighbours = this.countryService.getNeighbours(startingCountry.getCountryCode());

        TotalBudget totalBudget = new TotalBudget();
        totalBudget.setTotalBudget(inputDto.getTotalBudget());
        totalBudget.setCurrency(inputDto.getCurrency());

        Result result =
                getFinalResult(totalBudget, inputDto.getBudgetPerCountry(), neighbours);

        return result;

    }

    private Result getFinalResult(TotalBudget totalBudget, BigDecimal budgetPerCountry, ArrayList<String> neighbours) {

        int fullTravelCycleCounter;
        int leftOverBudget;

        int totalSumBudget = totalBudget.getTotalBudget().intValue();
        int budgetEachCountry = budgetPerCountry.intValue();
        Result result = new Result();

        int sumNeededForFullTravelCycle = neighbours.size() * budgetEachCountry;

        fullTravelCycleCounter = totalSumBudget / sumNeededForFullTravelCycle;
        leftOverBudget = totalSumBudget - (sumNeededForFullTravelCycle * fullTravelCycleCounter);

        result.setTravelAroundCountriesCount(fullTravelCycleCounter);
        result.setLeftOverBudget(BigDecimal.valueOf(leftOverBudget));

        HashMap<String, BigDecimal> currencyMap =
                currencyNeededForEachCountry(fullTravelCycleCounter, totalBudget.getCurrency(), neighbours);
        result.setNeededCurrencyForEachCountry(currencyMap);
        return result;


        //Previous code
        /*
        int fullTravelCycleCounter = 0;
        double leftOverBudget = 0;
        double totalSum = totalBudget.getTotalBudget().doubleValue();
        double budgetEachCountry = budgetPerCountry.doubleValue();
        Result result = new Result();

        double sumNeededForFullTravelCycle = (double) neighbours.size() * budgetEachCountry;

        boolean enoughBudget = true;
        while (enoughBudget) {
            if (totalSum - sumNeededForFullTravelCycle < 0) {
                leftOverBudget = totalSum;
                enoughBudget = false;
            } else {
                totalSum -= sumNeededForFullTravelCycle;
                fullTravelCycleCounter++;
            }
        }

        result.setTravelAroundCountriesCount(fullTravelCycleCounter);
        result.setLeftOverBudget(BigDecimal.valueOf(leftOverBudget));
        LinkedHashMap<String, BigDecimal> currencyMap =
                currencyNeededForEachCountry(fullTravelCycleCounter, totalBudget.getCurrency(), neighbours);
        result.setNeededCurrencyForEachCountry(currencyMap);
        return result;
        */
    }

    private HashMap<String, BigDecimal> currencyNeededForEachCountry(int fullTravelCycleCounter, String currency, ArrayList<String> neighbours) {
        HashMap<String, BigDecimal> map = new HashMap<>();

        for (String neighbour : neighbours) {
            try {
                Country neighbourOfSelectedCountry = this.countryRepository
                        .findByCountryCode(neighbour);
                String currencyOfNeighbour = neighbourOfSelectedCountry.getCurrencyCode();

                double neededCurrency = this.countryService.
                        getExchangeRate(currencyOfNeighbour, currency)
                        .doubleValue();

                neededCurrency *= (fullTravelCycleCounter * 100);
                map.put(neighbour + " " + currencyOfNeighbour, BigDecimal.valueOf(neededCurrency).setScale(2, RoundingMode.HALF_EVEN));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;

        //Previous Code
        /*
                LinkedHashMap<String, BigDecimal> map = new LinkedHashMap<>();
        neighbours.
                forEach(n -> {
                    try {
                        Country neighbour = this.countryRepository
                                .findByCountryCode(n);
                        String currencyOfNeighbour = neighbour.getCurrencyCode();

                        double neededCurrency = this.countryService.
                                getExchangeRate(currencyOfNeighbour, currency)
                                .doubleValue();
                        neededCurrency *= (fullTravelCycleCounter * 100);
                        map.put(n + " " + currencyOfNeighbour, BigDecimal.valueOf(neededCurrency).setScale(2, RoundingMode.HALF_EVEN));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        */
    }
}
