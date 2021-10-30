package org.hisp.dhis.utils;

import com.github.javafaker.Faker;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility methods for random data generation
 *
 * @author Gintare Vilkelyte
 * @author Luciano Fiandesio
 */
public class DataRandomizer
{
    private static Faker faker;

    public static Faker faker()
    {
        if ( faker == null )
        {
            faker = new Faker();
        }

        return faker;
    }

    /**
     * Generates a { Point } based on randomized coordinates
     *
     * @return a { Point}
     */
    public static Point randomPoint()
    {
        double latitude = (Math.random() * 180.0) - 90.0;
        double longitude = (Math.random() * 360.0) - 180.0;
        GeometryFactory geometryFactory = new GeometryFactory();
        /* Longitude (= x coord) first ! */
        return geometryFactory.createPoint( new Coordinate( longitude, latitude ) );
    }

    /**
     * Returns random string containing 6 alphabetical characters.
     *
     * @return a String
     */
    public static String randomString()
    {
        return RandomStringUtils.randomAlphabetic( 6 );
    }

    /**
     * Returns random string of alphabetical characters.
     *
     * @param size the size of the string
     * @return a String
     */
    public static String randomString( int size )
    {
        return RandomStringUtils.randomAlphabetic( size );
    }

    /**
     * Returns random entity name containing static string joined with 6 random
     * alphabetical characters
     *
     * @return a random name
     */
    public static String randomEntityName()
    {
        return "AutoTest entity " + randomString();
    }

    /**
     * Generates a random sequence of integers
     *
     * @param collectionSize max size of collection
     * @param max            max value of each integer
     * @return a List of Integer
     */
    public static List<Integer> randomSequence( int collectionSize, int max )
    {
        List<Integer> indexes = new ArrayList<>();
        if ( collectionSize == 1 )
        {
            indexes.add( 0 );
        }
        else
        {
            // create a list of ints from 0 to collection size (0,1,2,3,4...)
            indexes = IntStream.range( 0, collectionSize - 1 ).boxed()
                .collect( Collectors.toCollection( ArrayList::new ) );
            // randomize the list
            Collections.shuffle( indexes );
            if ( max > collectionSize )
            {
                max = collectionSize;
            }
            indexes = indexes.subList( 0, max - 1 );
        }
        return indexes;
    }

    /**
     * Generates a random integer
     *
     * @param min the minimum boundary for the generated int
     * @param max the maximum boundary for the generated int
     * @return a random int
     */
    public static int randomIntInRange( int min, int max )
    {
        return faker().instance().number().numberBetween( min, max );
    }

    /**
     * Generates a random integer
     *
     * @param decimals maximum number of places
     * @param min      minimum value
     * @param max      maximum value
     * @return a double
     */
    public static double randomDoubleInRange( int min, int max, int decimals )
    {
        return faker().number().randomDouble( decimals, min, max );
    }

    /**
     * Generates a random integer value
     *
     * @return a int
     */
    public static int randomInt()
    {
        return Math.toIntExact( faker().number().randomNumber() );
    }

    /**
     * Extracts a random element from a list
     *
     * @param list a List
     * @param <T>  type
     * @return a random element from the list
     */
    public static <T> T randomElementFromList( List<T> list )
    {
        Random rand = new Random();
        return list.get( rand.nextInt( list.size() ) );
    }

    public static <T> List<T> randomElementsFromList( List<T> list, int elements )
    {
        Collections.shuffle( list );
        if ( elements > list.size() )
        {
            elements = list.size();
        }
        return list.subList( 0, elements );
    }

    /**
     * Generates random boolean value
     *
     * @return a boolean
     */
    public static boolean randomBoolean()
    {
        return faker().bool().bool();
    }

    /**
     * Generates a random date in the future. The date is maximum 5 years from now
     *
     * @return a Date
     */
    public static Date randomFutureDate()
    {
        return faker().date().future( 1825, TimeUnit.DAYS );
    }

    /**
     * Generates a random date in the future. The date is maximum 5 years from now
     *
     * @param formatter a {DateTimeFormatter} for formatting the date
     * @return a Date formatted according to the specified {DateTimeFormatter}
     */
    public static String randomFutureDate( DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( randomFutureDate() ), formatter );
    }

    /**
     * Generates a random date in the past. The date is maximum 5 years in the past
     * from now
     *
     * @return a Date
     */
    public static Date randomPastDate()
    {
        return faker().date().past( 1825, TimeUnit.DAYS );
    }

    /**
     * Generates a random date in the past. The date is maximum 5 years in the past
     * from now
     *
     * @param formatter a {DateTimeFormatter} for formatting the date
     * @return a Date formatted according to the specified {DateTimeFormatter}
     */
    public static String randomPastDate( DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( randomPastDate() ), formatter );
    }

    /**
     * Generates a random date The date is maximum 5 years in the past from now or 5
     * years in the future from now
     *
     * @return a Date
     */
    public static Date getDate()
    {
        List<Date> dates = new ArrayList<>();
        dates.add( randomPastDate() );
        dates.add( randomFutureDate() );
        return randomElementFromList( dates );
    }

    /**
     * Generates a random date The date is maximum 5 years in the past from now or 5
     * years in the future from now
     *
     * @param formatter a DateTimeFormatter
     * @return a Date formatted according to the specified DateTimeFormatter
     */
    public static String randomDate( DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( getDate() ), formatter );
    }

    private static String formatDate( LocalDate localDate, DateTimeFormatter format )
    {
        return localDate.format( format );
    }

    private static LocalDate toLocalDate( Date dateToConvert )
    {
        return dateToConvert.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
    }
}
