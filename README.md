## Spring Security OAuth2 Simplest Demo

### Preface

The resource server and the authorization server are integrated in one project

**Protect Resource**

the healthCheck API

```
http://localhost:30000/api/v1.0/health

Response

active
```

**Denpendencies**

redis

**Run**

```
// bootRun
gradle dev bootRun
```

#### Grant Type: Authorization Code

1. Request for auth code
   
   ```
   http://localhost:30000/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read
   ```

2. Accept authorization and get auth code from callback url, e.g.

   ```
   http://localhost:9000/callback?code=Enn14m
   ```

3. Post form-data req to token endpoint

   ```
   POST http://localhost:30000/oauth/token application/x-www-form-urlencoded
   
   code: Enn14m
   grant_type: authorization_code
   redirect_url: localhost:9000/callback
   scope: read

   Response: application/json

   {
    "access_token": "475c9a13-a2eb-4613-a3c0-ce0e6f0e4c16",
    "token_type": "bearer",
    "refresh_token": "e6772098-beef-48ae-ae65-1ba33a3868c2",
    "expires_in": 119,
    "scope": "read"
   }
   ```

4. Request the protect resource

   ```
   GET http://localhost:3000/api/v1.0/health
   Header
     Authorization: Bearer 475c9a13-a2eb-4613-a3c0-ce0e6f0e4c16

   Response
  
   active
   ```

#### Grant Type: Implicit

1. Request for auth

   ```
   GET http://localhost:30000/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=token&scope=read&state=abc
   ```

2. Accept authorization and get access token from callback
   
   according to the OAuth2 specification, the res of implicit grant must not contain the refresh_token

   ```
   callback url

   http://localhost:9000/callback#access_token=00f086b5-a684-4f30-b93c-3c78f666455e&token_type=bearer&state=abc&expires_in=119
   ```

3. Request the protect resource

   ```
   GET http://localhost:3000/api/v1.0/health
   Header
     Authorization: Bearer 00f086b5-a684-4f30-b93c-3c78f666455e

   Response
  
   active
   ```

#### Grant Type: Password

1. Request for access token by post form-data to the token endpoint

   ```
   POST http://localhost:30000/oauth/token application/x-www-form-urlencoded
   
   grant_type: password
   username: dev
   password: 123
   scope: read

   Response application/json
   {
      "access_token": "58ae695b-9196-4d5a-912e-0aceae11517a",
      "token_type": "bearer",
      "refresh_token": "ea5321e3-3bc4-4370-9a54-76ac7b96364a",
      "expires_in": 119,
      "scope": "read"
   }
   ```

2. Request the protect resource

   ```
   GET http://localhost:3000/api/v1.0/health
   Header
     Authorization: Bearer 58ae695b-9196-4d5a-912e-0aceae11517a

   Response
  
   active
   ```

#### Grant Type: Client Credentials

1. Request for access token by post form-data to the token endpoint

   like implicit grant, client credentials does not provide the refresh_token

   ```
   POST http://localhost:30000/oauth/token application/x-www-form-urlencoded
   
   grant_type: client_credentials
   scope: admin

   Response

   {
      "access_token": "b4f527f6-fa5c-43e5-a0e0-cdb3450fbadc",
      "token_type": "bearer",
      "expires_in": 119,
      "scope": "admin"
   }
   ```

2. Request the protect resource

   ```
   GET http://localhost:3000/api/v1.0/health
   Header
     Authorization: Bearer b4f527f6-fa5c-43e5-a0e0-cdb3450fbadc

   Response
  
   active
   ```

### Refresh Token

1. Request for a new access token by post form-data to the token endpoint

   ```
   POST http://localhost:30000/oauth/token application/x-www-form-urlencoded
      
   grant_type: refresh_token
   scope: read
   refresh_token: 51d16ec3-05be-4dd9-8844-17fd21ddd4b2

   Response

   {
      "access_token": "503f48b4-4b4d-444b-b4d8-38648432d015",
      "token_type": "bearer",
      "refresh_token": "51d16ec3-05be-4dd9-8844-17fd21ddd4b2",
      "expires_in": 299,
      "scope": "read"
   }
   ```