package org.hisp.dhis.utils;

import com.github.javafaker.Faker;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class DataRandomizer
{
    public static Point randomPoint()
    {
        double latitude = (Math.random() * 180.0) - 90.0;
        double longitude = (Math.random() * 360.0) - 180.0;
        GeometryFactory geometryFactory = new GeometryFactory();
        /* Longitude (= x coord) first ! */
        return geometryFactory.createPoint( new Coordinate( longitude, latitude ) );
    }

    public static String randomString()
    {
        return RandomStringUtils.randomAlphabetic( 6 );
    }

    public static String randomEntityName()
    {
        return "AutoTest entity " + randomString();
    }

    public static List<Integer> randomSequence(int collectionSize, int max )
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

}
