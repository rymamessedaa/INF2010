package tp2;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Interview {
    /**
     * Finds all pairs within values which sum up to targetSum
     * @param values All possible values that can form a pair (can contain duplicates)
     * @param targetSum Pairs should add up to this
     * @return A collection containing all valid pairs with no permutations, but all combinations (empty collection if none found)
     */
    public Collection<MatchingPair> matchingPairs(Collection<Integer> values, Integer targetSum) {
        LinkedHashMap<Integer, Integer>  pastedValue = new LinkedHashMap();
        Collection<MatchingPair> pairs = new LinkedList<MatchingPair>();
        int value,diffrence;
        Iterator<Integer> iterator = values.iterator();

        while (iterator.hasNext()){
            value = iterator.next();
            diffrence = targetSum - value;

            if(pastedValue.containsKey(diffrence)){
                pairs.add(new MatchingPair(diffrence,value));

            }
            pastedValue.put(value,value);


        }

        return pairs;
    }
}

