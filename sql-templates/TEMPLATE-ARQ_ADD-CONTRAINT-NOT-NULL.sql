/**********************************************************************************
* Descrição: Script de adição de contraint na coluna da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
* Autor: #{Autor@Digite o nome do Autor}
* Data Criação: #{Data@Coloque a data de criação}
**********************************************************************************/

-----------------------------
-- #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
-----------------------------
set serveroutput on
prompt "[LOG] >>>>>> Executando script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"

DECLARE
    CONSTRAINT_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (CONSTRAINT_EXISTS , -01442);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela} ' ||
        'MODIFY #{Campo@Campo que sofrerá alteração@30} CONSTRAINT #{Nome da constraint@Nome padrão da constraint. Ex.: NN_DOCUMENTO_APRESENTAR_11@30} NOT NULL';    

    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A coluna #{Campo@Campo que sofrerá alteração@30} foi modificada com sucesso!');
    
EXCEPTION
    WHEN CONSTRAINT_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna #{Campo@Campo que sofrerá alteração@30} a ser modificada para NOT NULL já é NOT NULL.');
END;
/

prompt "[LOG] <<<<<< Fim da execução do script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"