/**********************************************************************************
* Descrição: Script de remoção de constraint na #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
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
    PRAGMA EXCEPTION_INIT (CONSTRAINT_EXISTS , -02443);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela} DROP CONSTRAINT #{Constraint@Constraint a ser excluído@30}';

    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A #{Campo@Constraint a ser excluído@30} foi excluída com sucesso!');
    
EXCEPTION
    WHEN CONSTRAINT_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A #{Campo@Constraint a ser excluído@30} que está sendo excluída já não existe.');
END;
/

prompt "[LOG] <<<<<< Fim da execucao do script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"