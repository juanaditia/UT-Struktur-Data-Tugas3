import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Tugas3Graph {

    // Menentukan nama dan nilai dari 8 node
    // Angka 43 merupakan dua digit terakhir NIM
    static final String[] NAMA = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"};
    static final int[] NILAI = {10, 24, 31, 15, 28, 36, 52, 43};

    // Menyimpan hubungan antar node menggunakan adjacency list
    static final List<List<Integer>> graph = new ArrayList<>();

    // Menambahkan hubungan dua arah antar node
    static void tambahSisi(int x, int y) {
        graph.get(x).add(y);
        graph.get(y).add(x);
    }

    // Menggabungkan nama dan nilai node, contohnya a1(10)
    static String label(int node) {
        return NAMA[node] + "(" + NILAI[node] + ")";
    }

    // 1: Mencari angka menggunakan Depth First Search (DFS)
    static boolean dfs(int node, int target, boolean[] visited, List<String> urutan) {

        // Menandai node yang sudah dikunjungi agar tidak dikunjungi kembali
        visited[node] = true;
        urutan.add(NAMA[node]);
        System.out.println("Kunjungi " + label(node));

        // Menghentikan pencarian jika nilai node sesuai dengan target
        if (NILAI[node] == target) return true;

        // Memeriksa setiap node yang terhubung dan belum dikunjungi
        for (int next : graph.get(node)) {
            if (!visited[next]) {

                // Memanggil method DFS kembali untuk menelusuri lebih dalam
                if (dfs(next, target, visited, urutan)) return true;

                // Kembali ke node sebelumnya jika target belum ditemukan
                System.out.println("Kembali ke " + NAMA[node]);
            }
        }

        // Mengembalikan false jika target tidak ditemukan
        return false;
    }

    // 2: Mencari angka menggunakan Breadth First Search (BFS)
    static boolean bfs(int start, int target, List<String> urutan) {

        // Menyiapkan visited dan queue untuk proses pencarian
        boolean[] visited = new boolean[NAMA.length];
        Queue<Integer> queue = new ArrayDeque<>();

        // Memasukkan node awal ke dalam queue
        queue.add(start);
        visited[start] = true;

        // Melakukan pencarian selama queue masih berisi node
        while (!queue.isEmpty()) {

            // Mengambil node pertama dari queue
            int node = queue.poll();
            urutan.add(NAMA[node]);
            System.out.println("Kunjungi " + label(node));

            // Menghentikan pencarian jika target ditemukan
            if (NILAI[node] == target) return true;

            // Memasukkan node tetangga yang belum dikunjungi ke dalam queue
            for (int next : graph.get(node)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }

            // Menampilkan isi queue pada setiap tahap pencarian
            List<String> isiQueue = new ArrayList<>();
            for (int next : queue) isiQueue.add(NAMA[next]);
            System.out.println("Queue: " + isiQueue);
        }

        // Mengembalikan false jika target tidak ditemukan
        return false;
    }

    public static void main(String[] args) {

        // Menentukan angka yang dicari dengan nilai default 43
        int target = args.length > 0 ? Integer.parseInt(args[0]) : 43;

        // Membuat adjacency list untuk masing-masing node
        for (int i = 0; i < NAMA.length; i++)
            graph.add(new ArrayList<>());

        // Menentukan hubungan antar node pada graph
        tambahSisi(0, 1); // a1 - a2
        tambahSisi(0, 2); // a1 - a3
        tambahSisi(1, 3); // a2 - a4
        tambahSisi(1, 4); // a2 - a5
        tambahSisi(3, 5); // a4 - a6
        tambahSisi(4, 6); // a5 - a7
        tambahSisi(2, 6); // a3 - a7
        tambahSisi(2, 7); // a3 - a8

        // Menampilkan target dan hubungan antar node
        System.out.println("Target pencarian: " + target);

        for (int i = 0; i < NAMA.length; i++) {
            List<String> tetangga = new ArrayList<>();
            for (int next : graph.get(i)) tetangga.add(NAMA[next]);

            System.out.println(label(i) + " -> " + tetangga);
        }

        // Menjalankan pencarian DFS dimulai dari node a1
        List<String> urutanDFS = new ArrayList<>();
        System.out.println("\n=== DFS ===");

        boolean hasilDFS = dfs(0, target, new boolean[NAMA.length], urutanDFS);

        // Menampilkan urutan dan hasil pencarian DFS
        System.out.println("Urutan DFS: " + urutanDFS);
        System.out.println("Hasil DFS: " + (hasilDFS ? "Ditemukan" : "Tidak ditemukan"));

        // Menjalankan pencarian BFS dimulai dari node a1
        List<String> urutanBFS = new ArrayList<>();
        System.out.println("\n=== BFS ===");

        boolean hasilBFS = bfs(0, target, urutanBFS);

        // Menampilkan urutan dan hasil pencarian BFS
        System.out.println("Urutan BFS: " + urutanBFS);
        System.out.println("Hasil BFS: " + (hasilBFS ? "Ditemukan" : "Tidak ditemukan"));
    }
}