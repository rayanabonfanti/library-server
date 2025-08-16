# Library Server

> Projeto de estudo em Java para gerenciamento de empréstimos de livros, utilizando **Collections (List, Set e Map)** e boas práticas de programação.

---

## Descrição

O **Library Server** é um sistema simples de backend para simular o funcionamento de uma biblioteca. O projeto foi desenvolvido com foco em:

- **Collections do Java**:
    - **List** → Histórico de empréstimos.
    - **Set** → Usuários cadastrados (garantindo unicidade).
    - **Map** → Empréstimos atuais (livro → usuário).
- **Boas práticas de programação**:
    - Separação de responsabilidades (Model / Repository / Service / Main).
    - Encapsulamento em POJOs.
    - Código modular e fácil de evoluir.
- **Funcionalidades principais**:
    - Cadastro de usuários e livros.
    - Empréstimo de livros (um livro por usuário de cada vez).
    - Devolução de livros.
    - Histórico de empréstimos com registro de ações.
    - Marcação de empréstimos expirados após 7 dias.

---

## Estrutura do projeto
```text
library-server/
├─ src/
│  └─ main/
│     └─ java/
│        └─ com/
│           └─ libraryserver/
│              ├─ model/
│              │  ├─ Book.java
│              │  ├─ User.java
│              │  └─ LoanRecord.java
│              ├─ repositories/
│              │  ├─ BookRepository.java
│              │  ├─ UserRepository.java
│              │  └─ LoanRepository.java
│              ├─ service/
│              │  └─ LibraryService.java
│              └─ Main.java
└─ README.md
```

---

## Funcionalidades

1. **Cadastro de usuários**
    - Cada usuário deve ter ID único.
    - Garantia de que nomes não se repetem usando **Set**.

2. **Cadastro de livros**
    - Cada livro tem ID, título e autor.

3. **Empréstimo de livros**
    - Um livro pode ser emprestado para apenas um usuário por vez (**Map**).
    - O método `borrowBook(userId, bookId)` retorna `true` se o empréstimo for bem-sucedido, `false` caso o livro já esteja emprestado.

4. **Devolução de livros**
    - O método `returnBook(bookId, userId)` devolve o livro e registra a ação no histórico.

5. **Histórico de empréstimos**
    - Mantido em **List**, registrando BORROW e RETURN.
    - Permite consulta completa das ações realizadas.

6. **Empréstimos expirados**
    - Cada empréstimo tem validade de 7 dias.
    - Após esse período, é marcado como **EXPIRED** no histórico.
    - Método `expiredLoans()` retorna todos os empréstimos vencidos.

---

## Conceitos aplicados

- **Collections do Java**
    - **List** → armazenamento ordenado do histórico de ações.
    - **Set** → garantia de unicidade dos usuários.
    - **Map** → mapeamento de empréstimos atuais (chave: livro, valor: usuário).

- **Concorrência**
    - Uso de **ConcurrentHashMap** e **Collections.synchronizedList** para segurança em múltiplas threads.

- **Orientação a objetos**
    - Separação de **Model, Repository e Service**.
    - Encapsulamento de dados em POJOs.
    - Métodos claros, pequenos e reutilizáveis.

- **Boas práticas**
    - Validação de IDs de usuários e livros.
    - Histórico detalhado das ações.
    - Modularidade para futura integração com REST API ou banco de dados.

---

## Como rodar

1. Clone o projeto:

```bash
git clone https://github.com/seu-usuario/library-server.git
```

##	Compile e rode:
```bash
javac -d out src/main/java/com/libraryserver/**/*.java
java -cp out com.libraryserver.Main
```

## Saída esperada (exemplo):
```bash
Status borrowing to user 1: true
Status borrowing to user 2: true
Status borrowing to user 3: false
Current Loans: {101=1, 102=2}
History: 
LoanRepository: 1 -> 101: BORROW at 2025-08-08T17:47:12.943856Z (EXPIRED)
LoanRepository: 2 -> 102: BORROW at 2025-08-16T17:47:12.941572Z
LoanRepository: 1 -> 101: RETURN at 2025-08-16T17:47:12.941833Z
```

## Possíveis evoluções
- Transformar em **API REST com Spring Boot**.
- Persistir dados em **banco de dados** (MySQL, PostgreSQL).
- Agendar verificações automáticas de **empréstimos expirados**.
- Criar uma **interface web** para visualização de empréstimos e histórico.

---

## Conclusão
O **Library Server** é um projeto de estudo para praticar **Collections do Java**, concorrência, orientação a objetos e boas práticas de programação, funcionando como um backend simplificado de biblioteca. Ideal para mostrar em portfólio estudos e domínio de conceitos de Java.
