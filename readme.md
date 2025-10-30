# 📸 GCam XML Hub

## Visão Geral do Projeto

O GCam XML Hub é um aplicativo mobile desenvolvido em Kotlin com Jetpack Compose, focado em ser o ponto central para usuários de Google Camera (GCam) compartilharem, buscarem e baixarem configurações XML personalizadas.

Essas configurações XML são essenciais para otimizar o desempenho da GCam em diferentes dispositivos, permitindo que os usuários atinham a máxima qualidade de imagem.

## Principais Funcionalidades

- **Feed de XMLs**: Exibição de configurações XML em cards harmoniosos, com detalhes como autor, versão da GCam e dispositivos compatíveis.
- **Controle de Acesso**: Gerenciamento do limite de upload de XMLs por usuário (simulando um sistema de assinatura/nível).
- **Upload de Configurações**: Formulário robusto para usuários enviarem suas próprias configurações XML para a comunidade.
- **Feedback em Tempo Real**: Sistema de Likes e Downloads para medir a popularidade das configurações.

## 💻 Tecnologias Utilizadas

Este projeto é construído sobre a moderna stack Android e a plataforma Google Firebase:

- **Linguagem**: Kotlin
- **UI/Framework**: Jetpack Compose (Desenvolvimento de UI declarativa)
- **Arquitetura**: MVVM (Model-View-ViewModel) com Hilt (Injeção de Dependência)
- **Backend & Cloud**: Google Firebase
  - **Firestore**: Base de dados NoSQL para armazenar metadados dos XMLs, usuários e contagens de Likes/Downloads.
  - **Firebase Auth**: Para autenticação de usuários.
  - **Firebase Storage**: (Futuro) Para armazenar os próprios arquivos .xml.

## ⚙️ Configuração Local (Firebase Setup)

Para compilar e rodar o projeto localmente, é obrigatório configurar o acesso ao seu projeto Firebase, pois o arquivo de configuração contém chaves privadas e foi ignorado pelo `.gitignore`.

1. **Crie um Projeto no Firebase**: Acesse o [Firebase Console](https://console.firebase.google.com) e crie um novo projeto Android.

2. **Registre o Aplicativo**: Siga os passos no Console para registrar o aplicativo com o `applicationId` configurado: `dev.braian.gcamxmlhub`.

3. **Baixe o `google-services.json`**: Após o registro, o Firebase fornecerá um arquivo chamado `google-services.json`.

4. **Posicione o Arquivo**: Copie o arquivo `google-services.json` e coloque-o no diretório `app/` (raiz do módulo do aplicativo) do seu projeto:

```
GCamXmlHub/
├── app/
│   └── google-services.json  <-- O arquivo DEVE estar aqui
└── ...
```

5. **Sincronize**: No Android Studio, clique em `File > Sync Project with Gradle Files` para aplicar as configurações.

## 🚀 Como Rodar

1. Clone o repositório:
```bash
git clone https://github.com/BraianS/Gcam-Xml-Hub.git
```

2. Configure o Firebase (Passos acima).

3. Abra o projeto no Android Studio.

4. Execute em um emulador ou dispositivo físico.

## 🤝 Contribuições

Contribuições são bem-vindas! Se você tiver sugestões, relatórios de bugs ou quiser desenvolver uma nova feature, por favor, abra uma Issue ou envie um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a Licença [MIT](LICENSE)