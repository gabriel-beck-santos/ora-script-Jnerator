/**********************************************************************************
* Descrição: Script de alteração do tipo da coluna na #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
* Autor: #{Autor@Digite o nome do Autor}
* Data Criação: #{Data@Coloque a data de criação@@date}
**********************************************************************************/

-----------------------------
-- #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
-----------------------------
set serveroutput on
prompt "[LOG] >>>>>> Executando script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"

ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
    ADD #{Campo@Campo a ser alterado@30}_ VARCHAR2(4);

UPDATE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
    SET #{Campo@Campo a ser alterado@30}_ = TO_CHAR(#{Campo@Campo a ser alterado@30});

COMMIT;

ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
    DROP COLUMN #{Campo@Campo a ser alterado@30};

ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}
    RENAME COLUMN #{Campo@Campo a ser alterado@30}_ TO #{Campo@Campo a ser alterado@30};

prompt "[LOG] <<<<<< Fim da executando script de alteração da #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela}"