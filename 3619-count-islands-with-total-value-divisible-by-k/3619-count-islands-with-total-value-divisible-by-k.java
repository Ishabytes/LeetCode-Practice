import java.util.*;

class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 0) {
                    continue;
                }

                long sum = 0;
                Stack<int[]> st = new Stack<>();

                st.push(new int[]{i, j});
                sum += grid[i][j];
                grid[i][j] = 0;

                while (!st.isEmpty()) {
                    int[] cell = st.pop();
                    int x = cell[0];
                    int y = cell[1];

                    for (int d = 0; d < 4; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if (nx >= 0 && nx < m &&
                            ny >= 0 && ny < n &&
                            grid[nx][ny] > 0) {

                            sum += grid[nx][ny];
                            st.push(new int[]{nx, ny});
                            grid[nx][ny] = 0;
                        }
                    }
                }

                if (sum % k == 0) {
                    count++;
                }
            }
        }

        return count;
    }
}