public class Main {
    public static void main(String[] args) {
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";


        //long inicio = System.nanoTime(); // Marca o tempo inicial

        Grafo grafo = new Grafo(10000, 7); // Criação do grafo

        //long fim = System.nanoTime(); // Marca o tempo final
        //long tempoTotal = fim - inicio; // Calcula o tempo decorrido

        //System.out.println("Tempo para criar o grafo: " + (tempoTotal / 1_000) + " µs");

        grafo.imprimirGrafo();
        //grafo.getVertice(4);

        System.out.println( RED + "\n ---------------------------------------------- \n Busca em Profundidade: " + RESET);
        grafo.buscaProfundidade(1, 4);
        System.out.println(BLUE + "\n ---------------------------------------------- \n Busca em Largura: " + RESET);
        grafo.buscaLargura(1, 4);
        System.out.println(GREEN + "\n ---------------------------------------------- \n Busca em Profundidade Limitada: " + RESET);
        //grafo.buscaLimitada(1, 4, 3);


    }

}
