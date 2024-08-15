package sprint2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class sprint2_1sem_garras {

    static class Objeto {
        String descricao;
        String tipoGarra;

        Objeto(String descricao, String tipoGarra) {
            this.descricao = descricao;
            this.tipoGarra = tipoGarra;
        }
    }

    public static void main(String[] args) {
        List<Objeto> objetos = new ArrayList<>();
        // Leitura do arquivo e criação do vetor de registros
        try (InputStream is = sprint2_1sem_garras.class.getClassLoader().getResourceAsStream("sprint2/ObjetosGarras.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                System.out.println("Arquivo não encontrado!");
                return;
            }
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    objetos.add(new Objeto(partes[0].trim(), partes[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ordenação dos registros pela descrição
        Collections.sort(objetos, new Comparator<Objeto>() {
            @Override
            public int compare(Objeto o1, Objeto o2) {
                return o1.descricao.compareToIgnoreCase(o2.descricao);
            }
        });

        // Interface de texto simples
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Exibir todos os objetos");
            System.out.println("2. Buscar objeto por descrição");
            System.out.println("3. Adicionar novo objeto");
            System.out.println("4. Remover objeto por descrição");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("\nObjetos:");
                    for (Objeto obj : objetos) {
                        System.out.println("Descrição: " + obj.descricao + ", Tipo de Garra: " + obj.tipoGarra);
                    }
                    break;
                case 2:
                    System.out.print("\nDigite a descrição do objeto para buscar: ");
                    String descricaoBusca = scanner.nextLine();
                    int indice = buscaBinaria(objetos, descricaoBusca);
                    if (indice >= 0) {
                        System.out.println("Objeto encontrado: Descrição: " + objetos.get(indice).descricao + ", Tipo de Garra: " + objetos.get(indice).tipoGarra);
                    } else {
                        System.out.println("Objeto não encontrado.");
                    }
                    break;
                case 3:
                    System.out.print("\nDigite a descrição do novo objeto: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Digite o tipo de garra do novo objeto: ");
                    String novoTipoGarra = scanner.nextLine();
                    objetos.add(new Objeto(novaDescricao, novoTipoGarra));
                    Collections.sort(objetos, new Comparator<Objeto>() {
                        @Override
                        public int compare(Objeto o1, Objeto o2) {
                            return o1.descricao.compareToIgnoreCase(o2.descricao);
                        }
                    });
                    System.out.println("Objeto adicionado com sucesso.");
                    break;
                case 4:
                    System.out.print("\nDigite a descrição do objeto para remover: ");
                    String descricaoRemover = scanner.nextLine();
                    int indiceRemover = buscaBinaria(objetos, descricaoRemover);
                    if (indiceRemover >= 0) {
                        objetos.remove(indiceRemover);
                        System.out.println("Objeto removido com sucesso.");
                    } else {
                        System.out.println("Objeto não encontrado.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Método de busca binária
    private static int buscaBinaria(List<Objeto> objetos, String descricao) {
        int inicio = 0;
        int fim = objetos.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = objetos.get(meio).descricao.compareToIgnoreCase(descricao);
            if (comparacao < 0) {
                inicio = meio + 1;
            } else if (comparacao > 0) {
                fim = meio - 1;
            } else {
                return meio;
            }
        }
        return -1;
    }
}
