package mods.thecomputerizer.theimpossiblelibrary.api.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused") public class RandomHelper {
    
    public static <E> E getBasicRandomEntry(Collection<E> entries) {
        int index = entries.size();
        if(index==0) throw new IllegalArgumentException("Cannot get random entry of empty collection");
        if(index==1) return new ArrayList<>(entries).get(0);
        index = randomInt(index);
        for(E element : entries) {
            if(index==0) return element;
            index--;
        }
        return null; //Should be unreachable
    }

    public static <E extends WeightedEntry> @Nullable E getEntryAt(Iterable<E> entries, int weight) {
        for(E entry : entries) {
            weight-=entry.getWeight();
            if(weight<0) return entry;
        }
        return null;
    }

    public static <E extends WeightedEntry> int getTotalWeight(Iterable<E> entries) {
        int weight = 0;
        for(E entry : entries) weight+=entry.getWeight();
        return weight;
    }

    public static <E extends WeightedEntry> @Nullable E getWeightedEntry(Random rand, Iterable<E> entries) {
        return getEntryAt(entries,rand.nextInt(getTotalWeight(entries)));
    }
    
    private static <N extends Number> N parse(String name, String unparsed, N fallback, Function<String,N> fromString) {
        try {
            return fromString.apply(unparsed);
        } catch (NumberFormatException ex) {
            TILRef.logError("Failed to parse number for {} from {}! Using fallback {}",name,unparsed,fallback);
            return fallback;
        }
    }
    
    public static byte randomByte(byte range) {
        return randomByte((byte)0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static byte randomByte(byte b1, byte b2) {
        return (byte)randomInt(b1,b2); //Is this really how it should be done?
    }
    
    public static byte randomByte(String name, String unparsed, byte fallback) {
        return randomNum(name,unparsed,fallback,Byte::parseByte,RandomHelper::randomByte);
    }
    
    public static double randomDouble(double range) {
        return randomDouble(0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static double randomDouble(double d1, double d2) {
        return d1>=d2 ? d2+ThreadLocalRandom.current().nextDouble()*Math.abs(d1-d2) :
                d1+ThreadLocalRandom.current().nextDouble()*Math.abs(d2-d1);
    }
    
    public static double randomDouble(String name, String unparsed, double fallback) {
        return randomNum(name,unparsed,fallback,Double::parseDouble,RandomHelper::randomDouble);
    }
    
    public static float randomFloat(float range) {
        return randomFloat(0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static float randomFloat(float f1, float f2) {
        return f1>=f2 ? f2+ThreadLocalRandom.current().nextFloat()*Math.abs(f1-f2) :
                f1+ThreadLocalRandom.current().nextFloat()*Math.abs(f2-f1);
    }
    
    public static float randomFloat(String name, String unparsed, float fallback) {
        return randomNum(name,unparsed,fallback,Float::parseFloat,RandomHelper::randomFloat);
    }
    
    public static int randomInt(int range) {
        return randomInt(0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static int randomInt(int i1, int i2) {
        return i1>i2 ? i2+ThreadLocalRandom.current().nextInt(Math.abs(i1-i2)) :
                i1+ThreadLocalRandom.current().nextInt(Math.abs(i2-i1));
    }
    
    public static int randomInt(String name, String unparsed, int fallback) {
        return randomNum(name,unparsed,fallback,Integer::parseInt,RandomHelper::randomInt);
    }
    
    public static long randomLong(long range) {
        return randomLong(0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static long randomLong(long l1, long l2) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        if(l1==l2) return l1==0 ? 0 : rand.nextLong(Math.abs(l1))*(l1>0 ? 1 : -1);
        return l1>l2 ? rand.nextLong(l2,l1) : rand.nextLong(l1,l2);
    }
    
    public static long randomLong(String name, String unparsed, long fallback) {
        return randomNum(name,unparsed,fallback,Long::parseLong,RandomHelper::randomLong);
    }
    
    /**
     * Uses a fallback in case someone decides to add something that is not a number to a number parameter
     */
    private static <N extends Number> N randomNum(
            String name, String unparsed, N fallback, Function<String,N> fromString, BiFunction<N,N,N> fromRand) {
        if(StringUtils.isBlank(unparsed)) {
            TILRef.logWarn("String to parse blank value {} numerical parameter {}! Using fallback {}",
                           unparsed,name,fallback);
            return fallback;
        }
        int index = unparsed.indexOf(':');
        if(index<=0) return parse(name,unparsed,fallback,fromString);
        N min = parse(name,unparsed.substring(0,index-1),fallback,fromString);
        N max = parse(name,unparsed.substring(0,index-1),fallback,fromString);
        return fromRand.apply(min,max);
    }
    
    public static short randomShort(short range) {
        return randomShort((short)0,range);
    }
    
    /**
     Automatically determines min/max
     */
    public static short randomShort(short s1, short s2) {
        return (short)randomInt(s1,s2); //Is this really how it should be done?
    }
    
    public static short randomShort(String name, String unparsed, short fallback) {
        return randomNum(name,unparsed,fallback,Short::parseShort,RandomHelper::randomShort);
    }
    
    
    @Getter
    public static class WeightedEntry {

        protected final int weight;

        public WeightedEntry(int weight) {
            this.weight = weight;
        }
    }
}