
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] - b[0]
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            int time = arrival[i];

            // Free completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                available.add(busy.poll()[1]);
            }

            if (available.isEmpty()) {
                continue;
            }

            // Find next available server
            Integer server = available.ceiling(i % k);

            if (server == null) {
                server = available.first();
            }

            available.remove(server);

            count[server]++;
            busy.offer(new int[]{time + load[i], server});
        }

        List<Integer> ans = new ArrayList<>();
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                ans.add(i);
            }
        }

        return ans;
    }
}
