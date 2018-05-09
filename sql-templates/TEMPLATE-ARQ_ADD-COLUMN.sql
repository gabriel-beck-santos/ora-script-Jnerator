/**********************************************************************************
* Descrição: Script de adição de nova coluna na #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
* Autor: #{Autor@Digite o nome do Autor}
* Data Criação: #{Data@Coloque a data de criação@@date}
**********************************************************************************/

-----------------------------
-- #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
-----------------------------
set serveroutput on
prompt "[LOG] >>>>>> Executando script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"

DECLARE
    COLUMN_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (COLUMN_EXISTS , -01430);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela} ADD #{Campo@Campo a ser incluído@30} #{Tipo de dado@Coloque o tipo de dado. Ex.: VARCHAR2(10)}';
    EXECUTE IMMEDIATE 'COMMENT ON COLUMN #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}.#{Campo@Campo a ser incluído@30} IS ''#{Comentário@Digite um comentário valido para o campo}''';

    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A coluna #{Campo@Campo a ser incluído@30} foi adicionada com sucesso!');
    
EXCEPTION
    WHEN COLUMN_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna #{Campo@Campo a ser incluído@30} que está sendo incluída já existe na tabela.');
END;
/

prompt "[LOG] <<<<<< Fim da execucao do script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"