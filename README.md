# Ecommerce-MarketZip# Ecommerce MarketZip

## 📋 Descrição
Projeto Java para gerenciamento de um e-commerce simples (clientes, funcionários, produtos, pedidos e itens de pedido). Aplica POO com arquitetura MVC e padrão DAO, persistindo dados em banco relacional via JDBC. Inclui telas de login e de funcionário, controllers, DAOs, e testes básicos dos componentes de acesso a dados.

## 🚀 Como Executar

### ✅ Pré-requisitos
- Java JDK 24 instalado
- Maven 3.6+ instalado (ou use o Maven embutido do IDE)
- Banco de dados configurado conforme `resources/db.properties`

### 📝 Verificar instalação
```powershell
java -version
mvn -v
```

### 🎯 Passo a passo (Windows PowerShell)

#### 1) Navegar até a raiz do projeto
```powershell
cd "C:\Users\evers\Coisas da facul\POO 25.2\projeto terceira unidade\Ecommerce-MarketZip - Copia (2)\ecommercemarcketzip"
```

#### 2) Compilar e empacotar
```powershell
mvn clean package
```

#### 3) Executar a aplicação
Caso gere um jar executável em `target`, rode:
```powershell
java -cp target\classes;target\dependency\* App
```
Se seu `pom.xml` criar um uber-jar, use:
```powershell
java -jar target\ecommercemarcketzip-1.0.jar
```

Observação: A classe principal está em `src/main/java/App.java`. Ajuste o nome do pacote/classe no comando `-cp` conforme seu `pom.xml`.

### 🚀 Execução rápida
```powershell
cd "."; mvn clean package; java -cp target\classes App
```

## 🎮 Primeiro Uso
- Configure `resources/db.properties` com host, porta, base, usuário e senha.
- Execute a aplicação; a tela de login (`view/TelaLogin.java`) será apresentada.
- A navegação de funcionalidades pode ocorrer pela `TelaFuncionario.java` após autenticação.

## 🔧 Solução de Problemas
- Erro: `java.lang.ClassNotFoundException`
	- Verifique o classpath ao executar (`target\classes` e dependências).
- Erro: não conecta ao banco
	- Revise `resources/db.properties` e se a base está ativa.
- Erro: `Access denied for user`
	- Corrija usuário/senha e privilégios no banco.
- Erro: `Cannot find db.properties`
	- Garanta que o arquivo esteja em `src/main/resources` e copiado para `target/classes`.

## 📁 Estrutura do Projeto
```
ecommercemarcketzip/
├── pom.xml
└── src/
		└── main/
				├── java/
				│   ├── App.java
				│   ├── control/
				│   │   ├── ClienteController.java
				│   │   ├── FuncionarioController.java
				│   │   ├── ItemDePedidoController.java
				│   │   ├── PedidoController.java
				│   │   └── ProdutoController.java
				│   ├── model/
				│   │   ├── dao/
				│   │   │   ├── ClienteDAO.java
				│   │   │   ├── FuncionarioDAO.java
				│   │   │   ├── ItemDePedidoDAO.java
				│   │   │   ├── PedidoDAO.java
				│   │   │   ├── ProdutoDAO.java
				│   │   │   └── UsuarioDAO.java
				│   │   ├── db/
				│   │   │   ├── DatabaseConfig.java
				│   │   │   └── DB.java
				│   │   └── entity/
				│   │       ├── Cliente.java
				│   │       ├── Funcionario.java
				│   │       ├── ItemDePedido.java
				│   │       ├── Pedido.java
				│   │       ├── Produto.java
				│   │       └── Usuario.java
				│   ├── utils/
				│   │   ├── Logg.java
				│   │   └── Utils.java
				│   └── view/
				│       ├── TelaFuncionario.java
				│       └── TelaLogin.java
				└── resources/
						└── db.properties
```

## 🏗️ Funcionalidades
- Cadastro e autenticação de usuários (`Usuario`, `Cliente`, `Funcionario`).
- CRUD de produtos (`ProdutoController`, `ProdutoDAO`).
- Criação e gestão de pedidos e itens (`Pedido`, `ItemDePedido`).
- Telas de login e interface para operações do funcionário.
- Logs e utilitários em `utils/`.

## 🏛️ Arquitetura
- MVC: `model` (entidades e DAOs), `view` (telas), `control` (regras de negócio).
- DAO: isolamento da persistência (JDBC), com `DB` e `DatabaseConfig` para conexão.
- Entidades de domínio claras: `Cliente`, `Funcionario`, `Usuario`, `Produto`, `Pedido`, `ItemDePedido`.

## 💾 Banco de Dados
- Configuração em `src/main/resources/db.properties`.
- Conexão gerenciada por `model/db/DB.java` e `DatabaseConfig.java`.
- Inclui script `ecommercemarketzip.sql` para criação das tabelas.

## 🧪 Testes
- Testes de DAO em `model/dao/test/` (ex.: `ClienteDAOTest.java`, `TestePedidoDAO.java`).
- Para executar via Maven (se configurado):
```powershell
mvn -Dtest=ClienteDAOTest test
```
Se não houver `surefire` configurado, compile e execute manualmente pela IDE/CLI.

## 🛠️ Comandos Úteis
```powershell
# Limpar, compilar e instalar
mvn clean install

# Executar classe principal diretamente do target/classes
java -cp target\classes App

# Rodar um teste específico
mvn -Dtest=TesteProdutoDAO test
```

## 📄 Licença
Projeto acadêmico para fins educacionais, demonstrando POO, MVC e JDBC em Java.