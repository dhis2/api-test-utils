package org.hisp.dhis.utils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public interface Randomizer {
    /**
     * Extracts a random element from a list
     *
     * @param list a List
     * @param <T>  type
     * @return a random element from the list
     */
    default <T> T randomElementFromList( List<T> list )
    {
        return list.get( randomInt( list.size() ) );
    }

    /**
     * Return the underlying {@link Random } object
     *
     * @return a {@link Random } object
     */
    Random getRandom();

    /**
     * Generates a random integer
     *
     * @param bound the maximum boundary for the generated int
     * @return a random int
     */
    public int randomInt(int bound);

    /**
     * Extracts a random sublist of n elements from a list
     *
     * @param list a List
     * @param size the size of the sublist
     * @param <T>  type
     * @return a random sublist
     */
    <T> List<T> randomElementsFromList(List<T> list, int size);

    /**
     * Returns random string.
     *
     * @return a String
     */
    default String randomString(){
        return randomString(randomInt(20));
    }

    /**
     * Returns random string of alphabetical characters.
     *
     * @param size the size of the string
     * @return a String
     */
    String randomString( int size );

    /**
     * Generates a random integer
     *
     * @param min the minimum boundary for the generated int
     * @param max the maximum boundary for the generated int
     * @return a random int
     */
    int randomIntInRange( int min, int max );

    /**
     * Generates a random integer
     *
     * @param decimals maximum number of places
     * @param min      minimum value
     * @param max      maximum value
     * @return a double
     */
    double randomDoubleInRange( int min, int max, int decimals );

    /**
     * Generates random boolean value
     *
     * @return a boolean
     */
    boolean randomBoolean();

    /**
     * Generates random username
     *
     * @return a username
     */
    String randomUsername();

    /**
     * Generates random first name
     *
     * @return a first name
     */
    String randomFirstName();

    /**
     * Generates random last name
     *
     * @return a last name
     */
    String randomLastName();

    /**
     * Generates random address
     *
     * @return a address
     */
    String randomAddress();

    /**
     * Generates random national id
     *
     * @return a national id
     */
    String randomNationalId();

    /**
     * Generates random birthday date
     *
     * @return a birthday date
     */
    default Date randomAdultBirthday(){
        return randomBirthday(18, 80);
    };

    /**
     * Generates random birthday date with age
     * between minAge and maxAge
     *
     * @return a birthday date
     */
    Date randomBirthday(int minAge, int maxAge);

    /**
     * Generates random long text
     *
     * @param wordCount number of words in the text
     * @return a long text
     */
    String randomLongText( int wordCount );

    /**
     * Generates random phone number
     *
     * @return a phone number
     */
    String randomPhoneNumber();

    /**
     * Generates a random date in the future. The date is maximum 5 years from now
     *
     * @return a Date
     */
    Date randomFutureDate();

    /**
     * Generates a random date in the future. The date is maximum 5 years from now
     *
     * @param formatter a {DateTimeFormatter} for formatting the date
     * @return a Date formatted according to the specified {DateTimeFormatter}
     */
    String randomFutureDate( DateTimeFormatter formatter );

    /**
     * Generates a random date in the past. The date is maximum 5 years in the past
     * from now
     *
     * @return a Date
     */
    Date randomPastDate();

    /**
     * Generates a random date in the past. The date is maximum 5 years in the past
     * from now
     *
     * @param formatter a {DateTimeFormatter} for formatting the date
     * @return a Date formatted according to the specified {DateTimeFormatter}
     */
    String randomPastDate( DateTimeFormatter formatter );

    /**
     * Generates a random date The date is maximum 5 years in the past from now or 5
     * years in the future from now
     *
     * @return a Date
     */
    default Date getDate()
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
    String randomDate( DateTimeFormatter formatter );
}
