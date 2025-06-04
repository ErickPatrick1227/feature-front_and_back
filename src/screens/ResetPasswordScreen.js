import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import "../style/custom.css";
import Logo from "../assets/logo.png";
import backgroundImage from "../assets/backG.jpg";

const RedefinirSenha = () => {
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");
  const [erro, setErro] = useState("");
  const [sucesso, setSucesso] = useState("");
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;

  const handleRedefinir = async () => {
    setErro("");
    setSucesso("");

    if (!senha || !confirmarSenha) {
      setErro("Preencha ambos os campos de senha");
      return;
    }
    if (senha !== confirmarSenha) {
      setErro("As senhas não conferem");
      return;
    }
    if (senha.length < 6) {
      setErro("A senha deve ter no mínimo 6 caracteres");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8081/api/auth/reset-password", {
        email,
        novaSenha: senha,
        confirmarSenha: confirmarSenha,
      });

      if (response.status === 200) {
        setSucesso("Senha redefinida com sucesso!");
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      } else {
        setErro("Erro ao redefinir a senha");
      }
    } catch (err) {
      setErro("Erro ao redefinir a senha");
    }
  };

  return (
    <div className="gradient">
      <div className="backG">
        <div className="backgroundImage" style={{ backgroundImage: `url(${backgroundImage})` }}>
          <div className="container">
            <img src={Logo} alt="ULife Logo" className="logoImage" />
            <h1 className="title">Redefinir Senha</h1>
            <p className="text">
              Defina uma nova senha para o email: <strong>{email}</strong>
            </p>

            <div className="inputWrapper">
              <label htmlFor="senha" className="formLabel">Nova Senha:</label>
              <input
                id="senha"
                type="password"
                value={senha}
                onChange={(e) => {
                  setSenha(e.target.value);
                  setErro("");
                  setSucesso("");
                }}
                className="input"
                placeholder="Digite a nova senha"
              />
            </div>

            <div className="inputWrapper">
              <label htmlFor="confirmarSenha" className="formLabel">Confirmar Senha:</label>
              <input
                id="confirmarSenha"
                type="password"
                value={confirmarSenha}
                onChange={(e) => {
                  setConfirmarSenha(e.target.value);
                  setErro("");
                  setSucesso("");
                }}
                className="input"
                placeholder="Confirme a nova senha"
              />
            </div>

            {erro && <div className="erro">{erro}</div>}
            {sucesso && <div className="sucesso">{sucesso}</div>}

            <button className="botao centeredButton" onClick={handleRedefinir}>
              <span className="textoBotao">Redefinir Senha</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RedefinirSenha;
