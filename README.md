# Trabalho de Programação OO  - IFF
**Alunos: Ana Carolina Andrade e Raphael Pereira**
<br/>
**Professor: Mark Douglas**

# 🎮 Jogo da Forca - Orientado a Objetos
Este é um jogo da forca desenvolvido com princípios de programação orientada a objetos (POO). O jogo segue as regras tradicionais da forca, com algumas funcionalidades adicionais, como registro de pontuações, ranking de jogadores e a possibilidade de arriscar todas as palavras de uma vez.
<br>
**🎲 A CADA RODADA, O JOGO ESCOLHE UM TEMA ALEATÓRIO E TRÊS PALAVRAS RELACIONADAS**


### 🎯 Rodadas
A rodada termina quando:

- ✅ O jogador arrisca e acerta as três palavras.

- 🔤 O jogador descobre todas as letras das palavras.

- ❌ O jogador comete 10 erros.

## 🕵️‍♂️ Como Adivinhar
O jogador pode:

- Escolher letras individualmente 

- Ou arriscar todas as palavras de uma só vez 🎲

- Só é permitido arriscar uma vez por rodada.

## 🗃️ Cadastro
- É possível cadastrar temas personalizados.
- Para cada tema, você pode adicionar várias palavras relacionadas.

### 🏆 Pontuação e Ranking
**A PONTUAÇÃO É BASEADA NO SEU DESEMPENHO EM CADA RODADA:**
- ✅ Acerto de uma letra: +10 pontos

- ❌ Erro ao tentar adivinhar uma letra: -5 pontos

- 🎯 Acerto ao arriscar a palavra completa: +50 pontos

- 💥 Erro ao arriscar a palavra completa: -20 pontos

- 🏅 Rodada concluída sem erros: Bônus de +30 pontos

- 🧠 Acertar todas as palavras (descobrindo ou arriscando corretamente): +100 pontos

- 🔒 Cada letra ainda encoberta no fim (se a rodada for vencida): +15 pontos por letra

  ###Estrutura do projeto

  src/
├── Main.java                            
│
├── entities/                             
│   ├── Jogador.java
│   ├── Palavra.java
│   ├── Rodada.java
│   └── Tema.java
│
├── repositories/                         
│   ├── JogadorRepository.java
│   ├── MemoriaJogadorRepository.java
│   ├── PalavraRepository.java
│   ├── MemoriaPalavraRepository.java
│   ├── TemaRepository.java
│   └── MemoriaTemaRepository.java
│
├── services/                           
│   ├── GerenciadorJogador.java
│   ├── GerenciadorRanking.java
│   ├── GerenciadorRodada.java
│   └── GerenciadorTema.java           
│
├── utils/                            
│   ├── Boneco.java                    
│   └── Validador.java                   
│
└── exceptions/
    └── RepositoryException.java        

