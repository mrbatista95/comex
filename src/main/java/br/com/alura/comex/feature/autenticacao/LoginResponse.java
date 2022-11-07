package br.com.alura.comex.feature.autenticacao;

public class LoginResponse {

    private String token;
    private String bearer;

    public LoginResponse(String token, String bearer){
        this.token = token;
        this.bearer = bearer;
    }

    public String getToken() {
        return token;
    }

    public String getBearer() {
        return bearer;
    }
}
