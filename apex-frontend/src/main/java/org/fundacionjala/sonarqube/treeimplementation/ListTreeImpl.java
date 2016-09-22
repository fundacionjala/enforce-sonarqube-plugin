package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.ListTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.*;

import static org.fundacionjala.sonarqube.tree.Tree.Kind.*;

public abstract class ListTreeImpl<T extends Tree> extends ApexTree implements ListTree<T> {

    private final List<T> list;
    private final List<SyntaxToken> separators;

    public ListTreeImpl(GrammarRuleKey grammarRuleKey, List<T> list) {
        super(grammarRuleKey);
        this.list = list;
        this.separators = Lists.newArrayList();
    }

    public ListTreeImpl(GrammarRuleKey grammarRuleKey, List<T> list, List<SyntaxToken> separators) {
        super(grammarRuleKey);
        this.list = list;
        this.separators = separators;
    }

    @Override
    public List<SyntaxToken> separators() {
        return separators;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        for (T t : list) {
            t.accept(visitor);
        }
    }

    @Override
    public Kind kind() {
        return LIST;
    }

    public Iterable<Tree> children() {
        return new InterleaveIterable(list, separators);
    }

    private class InterleaveIterable implements Iterable<Tree> {

        private final ImmutableList<Iterator<? extends Tree>> iterators;

        public InterleaveIterable(List<T> list, List<SyntaxToken> separators) {
            iterators = ImmutableList.of(list.iterator(), separators.iterator());
        }

        @Override
        public Iterator<Tree> iterator() {
            return new InterleaveIterator<>(iterators);
        }
    }

    private static class InterleaveIterator<E> extends AbstractIterator<E> {

        private final LinkedList<Iterator<? extends E>> iterables;

        public InterleaveIterator(List<Iterator<? extends E>> iterables) {
            super();
            this.iterables = new LinkedList<>(iterables);
        }

        @Override
        protected E computeNext() {
            while (!iterables.isEmpty()) {
                Iterator<? extends E> topIter = iterables.poll();
                if (topIter.hasNext()) {
                    E result = topIter.next();
                    iterables.offer(topIter);
                    return result;
                }
            }
            return endOfData();
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
