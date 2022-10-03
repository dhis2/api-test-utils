/*
 * Copyright (c) 2004-2022, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This Randomizer will return random values based on a seed.
 * Using the same seed will make the Randomizer to generate
 * the same sequence of random values.
 */
public class PredictableRandomizer implements Randomizer {

    private final Random rnd;

    private final Faker faker;

    public PredictableRandomizer( long seed )
    {
        this.rnd = new Random( seed );
        this.faker = new Faker( rnd );
    }

    @Override
    public Random getRandom() {
        return this.rnd;
    }

    @Override
    public int randomInt(int bound)
    {
        return rnd.nextInt(bound);
    }

    @Override
    public <T> List<T> randomElementsFromList( List<T> list, int size )
    {
        List<T> copiedList = new ArrayList<>(list);
        List<T> resultList = new ArrayList<>();

        for ( int i = 0; i < size && !copiedList.isEmpty(); i++) {
            T randomElementFromList = randomElementFromList( copiedList );
            resultList.add( copiedList.get( copiedList.indexOf( randomElementFromList ) ) );
            copiedList.remove( randomElementFromList );
        }

        return resultList;
    }

    @Override
    public String randomString()
    {
        return this.faker.programmingLanguage().name();
    }

    public String randomString( int size )
    {
        return this.faker.lorem().characters( size, true, false );
    }

    public int randomIntInRange( int min, int max )
    {
        return this.faker.number().numberBetween( min, max );
    }

    @Override
    public double randomDoubleInRange( int min, int max, int decimals )
    {
        return this.faker.number().randomDouble( decimals, min, max );
    }

    @Override
    public boolean randomBoolean()
    {
        return this.faker.bool().bool();
    }

    @Override
    public String randomUsername() {
        return this.faker.name().username();
    }

    @Override
    public String randomFirstName() {
        return this.faker.name().firstName();
    }

    @Override
    public String randomLastName() {
        return this.faker.name().lastName();
    }

    @Override
    public String randomAddress() {
        return this.faker.address().fullAddress();
    }

    @Override
    public String randomNationalId() {
        return this.faker.idNumber().valid();
    }

    @Override
    public Date randomBirthday() {
        return this.faker.date().birthday( 18, 80 );
    }

    @Override
    public String randomLongText( int wordCount ) {
        return this.faker.lorem().sentence( wordCount );
    }

    @Override
    public String randomPhoneNumber() {
        return this.faker.phoneNumber().phoneNumber();
    }

    @Override
    public Date randomFutureDate()
    {
        return this.faker.date().future( 1825, TimeUnit.DAYS );
    }

    @Override
    public String randomFutureDate( DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( randomFutureDate() ), formatter );
    }

    @Override
    public Date randomPastDate()
    {
        return this.faker.date().past( 1825, TimeUnit.DAYS );
    }

    @Override
    public String randomPastDate( DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( randomPastDate() ), formatter );
    }

    @Override
    public String randomDate(DateTimeFormatter formatter )
    {
        return formatDate( toLocalDate( getDate() ), formatter );
    }

    private String formatDate(LocalDate localDate, DateTimeFormatter format )
    {
        return localDate.format( format );
    }

    private LocalDate toLocalDate( Date dateToConvert )
    {
        return dateToConvert.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
    }
}
