
import java.util.*;

class Solution {
    int[] tin, tout, depth;
    int[][] up;
    List<Integer>[] adj;
    int timer = 0;
    int LOG;

    int[] bit;

    void update(int i, int val) {
        while (i < bit.length) {
            bit[i] ^= val;
            i += i & -i;
        }
    }

    int query(int i) {
        int ans = 0;
        while (i > 0) {
            ans ^= bit[i];
            i -= i & -i;
        }
        return ans;
    }

    void dfs(int u, int p) {
        tin[u] = ++timer;
        up[0][u] = p;

        for (int j = 1; j < LOG; j++) {
            up[j][u] = up[j - 1][up[j - 1][u]];
        }

        for (int v : adj[u]) {
            if (v != p) {
                depth[v] = depth[u] + 1;
                dfs(v, u);
            }
        }

        tout[u] = timer;
    }

    int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int t = a;
            a = b;
            b = t;
        }

        int diff = depth[a] - depth[b];

        for (int j = LOG - 1; j >= 0; j--) {
            if ((diff & (1 << j)) != 0) {
                a = up[j][a];
            }
        }

        if (a == b) return a;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][a] != up[j][b]) {
                a = up[j][a];
                b = up[j][b];
            }
        }

        return up[0][a];
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];

        dfs(0, 0);

        bit = new int[n + 2];
        char[] arr = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (arr[i] - 'a');
            update(tin[i], mask);
            update(tout[i] + 1, mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (arr[u] - 'a');
                int newMask = 1 << (c - 'a');
                int change = oldMask ^ newMask;

                update(tin[u], change);
                update(tout[u] + 1, change);

                arr[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int mask = query(tin[u])
                         ^ query(tin[v])
                         ^ (1 << (arr[ancestor] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }
}
