package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Wrapper used for bundling generic helper methods with a final Iterable instance
 */
@SuppressWarnings({"unchecked", "unused"})
public class Wrapperable<E> implements Iterable<E> {

    /**
     * Helper method for iterable suppliers
     */
    public static <E> Wrapperable<E> make(Supplier<Iterable<E>> supplier) {
        return new Wrapperable<>(supplier.get(),false);
    }

    public static <E> Wrapperable<E> make(Supplier<Iterable<E>> supplier, Consumer<Wrapperable<E>> settings) {
        Wrapperable<E> bundle = make(supplier);
        settings.accept(bundle);
        return bundle;
    }

    /**
     * Synchronized versions of the make methods
     */
    public static <E> Wrapperable<E> makeSynchronized(Supplier<Iterable<E>> supplier) {
        return new Wrapperable<>(supplier.get(),true);
    }

    public static <E> Wrapperable<E> makeSynchronized(Supplier<Iterable<E>> supplier, Consumer<Wrapperable<E>> settings) {
        Wrapperable<E> bundle = make(supplier);
        settings.accept(bundle);
        return bundle;
    }

    /**
     * Array versions of the make methods
     */
    public static <E> Wrapperable<E> makeArray(E ... elements) {
        List<E> list = new ArrayList<>(Arrays.asList(elements));
        return make(() -> list);
    }
    public static <E> Wrapperable<E> makeArray(Consumer<Wrapperable<E>> settings, E ... elements) {
        List<E> list = new ArrayList<>(Arrays.asList(elements));
        return make(() -> list,settings);
    }

    public static <E> Wrapperable<E> makeArray(Supplier<E[]> supplier) {
        return makeArray(supplier.get());
    }

    public static <E> Wrapperable<E> makeArray(Supplier<E[]> supplier, Consumer<Wrapperable<E>> settings) {
        return makeArray(settings,supplier.get());
    }

    /**
     * Synchronized versions of the makeArray methods
     */
    public static <E> Wrapperable<E> makeSynchronizedArray(E ... elements) {
        List<E> list = new ArrayList<>(Arrays.asList(elements));
        return makeSynchronized(() -> list);
    }
    public static <E> Wrapperable<E> makeSynchronizedArray(Consumer<Wrapperable<E>> settings, E ... elements) {
        List<E> list = new ArrayList<>(Arrays.asList(elements));
        return makeSynchronized(() -> list,settings);
    }

    public static <E> Wrapperable<E> makeSynchronizedArray(Supplier<E[]> supplier) {
        return makeSynchronizedArray(supplier.get());
    }

    public static <E> Wrapperable<E> makeSynchronizedArray(Supplier<E[]> supplier, Consumer<Wrapperable<E>> settings) {
        return makeSynchronizedArray(settings,supplier.get());
    }

    private final Iterable<E> iterable;

    public Wrapperable(Iterable<E> iterable, boolean isSynchronized) {
        if(isSynchronized) {
            if(iterable instanceof Collection<?>) iterable = Collections.synchronizedCollection((Collection<E>)iterable);
            else throw new UnsupportedOperationException("Cannot make synchronized instance of non collection iterable!");
        }
        this.iterable = fixInstance(iterable);
    }

    public void add(E element) {
        if(this.iterable instanceof Collection<?>)
            ((Collection<E>)this.iterable).add(element);
        else throw new UnsupportedOperationException("Cannot add to non collection iterable!");
    }

    /**
     * This may have weird results if the collection instance does not preserce order
     */
    public void add(int index, E element) {
        if(this.iterable instanceof List<?>) ((List<E>)this.iterable).add(index,element);
        else {
            int count = size();
            if(count==0 || index==count) this.add(element);
            else {
                E[] elementCopy = (E[])Array.newInstance(element.getClass(),count+1);
                if(index<0 || index>count) index = count;
                count = 0;
                for(E e : this.iterable) {
                    if(count==index) {
                        elementCopy[count] = element;
                        count++;
                    }
                    elementCopy[count] = e;
                    count++;
                }
                set(elementCopy);
            }
        }
    }

    public void addAll(Iterable<E> other) {
        if(this.iterable instanceof Collection<?>) {
            Collection<E> c = (Collection<E>)this.iterable;
            if(other instanceof Wrapperable<?>) {
                c.addAll(((Wrapperable<E>)other).get());
                return;
            }
            if(other instanceof Collection<?>) {
                c.addAll((Collection<E>)other);
                return;
            }
        }
        for(E element : other) add(element);
    }

    public void clear() {
        if(this.iterable instanceof Collection<?>)
            ((Collection<E>)this.iterable).clear();
        else throw new UnsupportedOperationException("Cannot clear non collection iterable!");
    }

    public Wrapperable<E> copyNotMatching(Supplier<Wrapperable<E>> supplier, Function<E,Boolean> matcher) {
        Wrapperable<E> w = supplier.get();
        for(E element : this)
            if(matcher.apply(element)) w.add(element);
        return w;
    }

    /**
     * Handles checking the instance of iterators that may be wrappable or mappable instances
     * This is done so the instanceof Collection checks do not fail when they should not
     */
    protected Iterable<E> fixInstance(Iterable<E> itr) {
        if(itr instanceof Mappable<?,?>) {
            ReferenceAPI.logWarn("Returning the entry set a mappable instance as an iterator!");
            return (Iterable<E>)((Mappable<?,?>)itr).entrySet();
        }
        if(itr instanceof Wrapperable<?>) return fixInstanceInner((Wrapperable<E>)itr);
        return itr;
    }

    /**
     * Recursive version of fixInstance
     */
    private Iterable<E> fixInstanceInner(Wrapperable<E> parent) {
        Iterable<E> next = parent.iterable;
        if(next instanceof Wrapperable<?>) return fixInstanceInner((Wrapperable<E>)next);
        return next;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        this.iterable.forEach(action);
    }

    public Collection<E> get() {
        if(this.iterable instanceof Collection<?>)
            return (Collection<E>)this.iterable;
        else throw new UnsupportedOperationException("Cannot get non collection iterable!");
    }

    public @Nullable E get(int index) {
        if(this.iterable instanceof List<?>) return ((List<E>)this.iterable).get(index);
        int count = size();
        if(index<0 || index>count) index = count;
        for(E element : get()) {
            if(count==index) return element;
            count++;
        }
        return null;
    }

    public List<E> getAsList() throws ClassCastException  {
        return (List<E>)getAsType(List.class,null);
    }

    public List<E> getAsList(Function<Wrapperable<E>,? extends List<E>> onCastException) {
        return (List<E>)getAsType(List.class,onCastException);
    }

    public List<E> getAsSynchronizedList() throws ClassCastException  {
        return (List<E>)getAsType(List.class,null);
    }

    public List<E> getAsSynchronizedList(Function<Wrapperable<E>,? extends List<E>> onCastException) {
        return (List<E>)getAsType(List.class,onCastException);
    }

    public Set<E> getAsSet() throws ClassCastException  {
        return (Set<E>)getAsType(Set.class,null);
    }

    public Set<E> getAsSet(Function<Wrapperable<E>,? extends Set<E>> onCastException) {
        return (Set<E>)getAsType(Set.class,onCastException);
    }

    public <T extends Collection<E>> T getAsType(Class<T> clazz) throws ClassCastException {
        return getAsType(clazz,null);
    }

    /**
     * Syntactic sugar...
     */
    public <T extends Collection<E>> T getAsType(Class<T> clazz, @Nullable Function<Wrapperable<E>,? extends Collection<E>> onCastException) {
        try {
            return clazz.cast(this.iterable);
        } catch(Exception ex) {
            String base = "Failed to cast backend iterable instance to ";
            if(Objects.isNull(onCastException))
                throw new ClassCastException(base+clazz.getName()+"! No input exception handler is present");
            ReferenceAPI.logError("{}{}! Input exception handler will be called",base,clazz,ex);
            return (T)onCastException.apply(this);
        }
    }

    public Class<?> getElementClass() {
        E element = getNonNullElement();
        if(Objects.isNull(element)) {
            ReferenceAPI.logWarn("Unable to get the element class of a wrappable instance! Is the instance empty?");
            return Object.class;
        }
        return element.getClass();
    }

    /**
     * Runs getFirstMatching with Objects#equals as the matcherFunc
     */
    public @Nullable E getFirstEquals(@Nullable Iterable<E> otherItr) {
        return getFirstMatching(otherItr,Objects::equals);
    }

    /**
     * Iterates through the other iterable for each element of the wrapped iterable and returns the matching element.
     * Both iterables are assumed to be ordered or the result may be inconsistent
     */
    public @Nullable E getFirstMatching(@Nullable Iterable<E> otherItr, BiFunction<E,E,Boolean> matcherFunc) {
        if(Objects.isNull(otherItr)) return null;
        for(E element : this)
            for(E otherElement : otherItr)
                if(matcherFunc.apply(element,otherElement)) return element;
        return null;
    }

    /**
     * Gets the first element in the collection that is not null. Returns null if nothing is found
     */
    public @Nullable E getNonNullElement() {
        E element = null;
        for(E e : get()) {
            if(Objects.nonNull(e)) {
                element = e;
                break;
            }
        }
        return element;
    }

    public void insertMatching(Collection<E> output, Function<E,Boolean> matcher) {
        forEach(val -> {
            if(matcher.apply(val)) output.add(val);
        });
    }

    public <C extends Collection<E>> C insertMatching(Supplier<C> outputSupplier, Function<E,Boolean> matcher) {
        C output = outputSupplier.get();
        forEach(val -> {
            if(matcher.apply(val)) output.add(val);
        });
        return output;
    }

    public boolean isEmpty() {
        return this.iterable instanceof Collection<?> ? ((Collection<E>)this.iterable).isEmpty() : size()==0;
    }

    public boolean isList() {
        return this.iterable instanceof List<?>;
    }

    public boolean isNotEmpty() {
        return this.iterable instanceof Collection<?> ? !((Collection<E>)this.iterable).isEmpty() : size()>=0;
    }

    public boolean isSet() {
        return this.iterable instanceof Set<?>;
    }

    public boolean isUnique() {
        return !isList() && !isSet();
    }

    @Override
    public Iterator<E> iterator() {
        return this.iterable.iterator();
    }

    public <M> Wrapperable<M> map(Supplier<Wrapperable<M>> supplier, Function<E,M> mapper) {
        Wrapperable<M> wrapperable = supplier.get();
        forEach(e -> wrapperable.add(mapper.apply(e)));
        return wrapperable;
    }

    public <M> void mapTo(Wrapperable<M> w, Function<E,M> mapper, boolean clearExisting, boolean removeNulls) {
        if(clearExisting) w.clear();
        forEach(e -> {
            M mapped = mapper.apply(e);
            if(Objects.nonNull(mapped)) w.add(mapped);
        });
    }

    public <M> void mapTo(Collection<M> c, Function<E,M> mapper, boolean clearExisting, boolean removeNulls) {
        if(clearExisting) c.clear();
        forEach(e -> {
            M mapped = mapper.apply(e);
            if(Objects.nonNull(mapped)) c.add(mapped);
        });
    }

    public <M,C extends Collection<M>> C mapTo(Supplier<C> supplier, Function<E,M> mapper, boolean removeNulls) {
        C c = supplier.get();
        mapTo(c,mapper,false,removeNulls);
        return c;
    }

    public Stream<E> parallelStream() {
        if(this.iterable instanceof Collection<?>) return ((Collection<E>)this.iterable).parallelStream();
        return StreamSupport.stream(spliterator(),true);
    }

    public void remove(E element) {
        if(this.iterable instanceof Collection<?>) ((Collection<E>)this.iterable).remove(element);
    }

    public boolean removeIf(Predicate<? super E> filter) {
        if(this.iterable instanceof Collection<?>) return ((Collection<E>)this.iterable).removeIf(filter);
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> itr = iterator();
        while(itr.hasNext()) {
            if(filter.test(itr.next())) {
                itr.remove();
                removed = true;
            }
        }
        return removed;
    }

    public void removeAll(Iterable<E> other) {
        if(this.iterable instanceof Collection<?>) {
            Collection<E> c = (Collection<E>)this.iterable;
            if(other instanceof Wrapperable<?>) {
                c.removeAll(((Wrapperable<E>)other).get());
                return;
            }
            if(other instanceof Collection<?>) {
                c.removeAll((Collection<E>)other);
                return;
            }
        }
        for(E element : other) remove(element);
    }

    /**
     * This may have weird results if the collection instance does not preserce order or if the elements are integers
     */
    public void removeIndex(int index) {
        if(this.iterable instanceof List<?>) ((List<E>)this.iterable).remove(index);
        else {
            int count = size()-1;
            if(count<=0) clear();
            E removal = null;
            if(index<0 || index>count) index = count;
            count = 0;
            for(E e : this.iterable) {
                if(count==index) {
                    removal = e;
                    break;
                }
                count++;
            }
            if(Objects.nonNull(removal)) remove(removal);
            else ReferenceAPI.logDebug("Failed to remove null element of wrappable instance");
        }
    }

    public final void set(E ... elements) {
        this.set(true,Arrays.asList(elements));
    }

    public final void set(boolean clearExisting, E ... elements) {
        this.set(clearExisting,Arrays.asList(elements));
    }

    public void set(Iterable<E> elements) {
        this.set(true,elements);
    }

    public void set(boolean clearExisting, Iterable<E> elements) {
        if(this.iterable instanceof Collection<?>) {
            Collection<E> c = (Collection<E>)this.iterable;
            if(clearExisting) c.clear();
            if(elements instanceof Collection<?>) c.addAll((Collection<E>)elements);
            else elements.forEach(c::add);
        } else throw new UnsupportedOperationException("Cannot set elements for non collection iterable!");
    }

    public int size() {
        if(this.iterable instanceof Collection<?>) return ((Collection<E>)this.iterable).size();
        final MutableInt sizeCounter = new MutableInt();
        this.iterable.forEach(e -> sizeCounter.add(1));
        return sizeCounter.getValue();
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.iterable.spliterator();
    }

    public Stream<E> stream() {
        if(this.iterable instanceof Collection<?>) return ((Collection<E>)this.iterable).stream();
        return StreamSupport.stream(spliterator(), false);
    }

    public E[] toArray() {
        return (E[])Array.newInstance(getElementClass(),size());
    }

    @Override
    public String toString() {
        return this.iterable.toString();
    }
}
