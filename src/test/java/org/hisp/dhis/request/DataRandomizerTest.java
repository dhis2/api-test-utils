package org.hisp.dhis.request;

import org.hisp.dhis.utils.DataRandomizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class DataRandomizerTest
{
    @ParameterizedTest
    @CsvSource( {
        "1,1,1"
    } )
    public void shouldGenerateRandomNumberBetween(int min, int max, int expected) {
        int random = DataRandomizer.randomIntInRange( min, max );

        assertEquals(random, expected);
    }
}
