# acesso-api
Desafio Cadastro/Login

### Exemplo para consumo da API:

**Cadastrar:**
POST https://acesso-api.herokuapp.com/cadastro

```json
{
    "name": "João da Silva",
    "email": "joao@silva.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "987654321",
            "ddd": "21"
        },
        {
        	"number": "999999999",
        	"ddd": "11"
        }
    ]
}
```

**Login:**
POST https://acesso-api.herokuapp.com/login

```json
{
	"email": "joao@silva.org",
    "password": "hunter2"
}
```

**Perfil:**
GET https://acesso-api.herokuapp.com/perfil/1
Passar um Bearer token
