public class Main {
    public static void main(String[] args) {
        // String de cores, apenas para impressao no terminal
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";

        Grafo grafo = null;
        boolean criado = false;

        // Tenta gerar o grafo até conseguir
        while (!criado) {
            try {
                grafo = new Grafo(10000, 9999);
                criado = true;
            } catch (Exception e) {
                System.out.println(YELLOW + "Não foi possível criar o grafo, tentando novamente..." + RESET);
            }
        }

        // Impressao das buscas e resultados
        System.out.println( RED + "\n ---------------------------------------------- \n Busca em Profundidade: " + RESET);
        long inicio = System.nanoTime();
        grafo.buscaProfundidade(0, 9999);
        long fim = System.nanoTime(); // Marca o tempo final
        long tempoTotal = fim - inicio; // Calcula o tempo decorrido
        System.out.println("Tempo para encontrar: " + (tempoTotal / 1_000) + " µs");

        System.out.println(BLUE + "\n ---------------------------------------------- \n Busca em Largura: " + RESET);
        long inicioLargura = System.nanoTime();
        grafo.buscaLargura(0, 9999);
        long fimLargura = System.nanoTime(); // Marca o tempo final
        long tempoTotalLargura = fimLargura - inicioLargura; // Calcula o tempo decorrido
        System.out.println("Tempo para encontrar: " + (tempoTotalLargura / 1_000) + " µs");

        System.out.println(GREEN + "\n ---------------------------------------------- \n Busca em Profundidade Limitada: " + RESET);
        long inicioLimitada = System.nanoTime();
        grafo.buscaProfundidadeLimitada(0, 9999, 50);
        long fimLimitada = System.nanoTime(); // Marca o tempo final
        long tempoTotalLimitada = fimLimitada - inicioLimitada; // Calcula o tempo decorrido
        System.out.println("Tempo para encontrar: " + (tempoTotalLargura / 1_000) + " µs");

    }

}
