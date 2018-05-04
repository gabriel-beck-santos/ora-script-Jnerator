/**********************************************************************************
* Descrição: Script de adição de nova opção da aplicação.
* Autor: #{Autor do script}
* Data Criação: #{Data de criação}
**********************************************************************************/

prompt "[LOG] >>>>>> Executando script de inserção na SEFAZ_SEG.TA_OPCAO_APLICACAO"

DECLARE

    vCountAplicacaoModuloSistema NUMBER := 0;
    vCountModuloSistema NUMBER := 0;

BEGIN
    SELECT COUNT(*) 
      INTO vCountAplicacaoModuloSistema
      FROM SEFAZ_SEG.TA_APLICACAO_MODULO AM
     INNER JOIN SEFAZ_SEG.TA_MODULO_SISTEMA SM ON AM.IDENTIFICACAO_MODULO_SISTEMA = SM.IDENTIFICACAO_MODULO_SISTEMA
     WHERE SM.ABREVIACAO_MODULO = '#{Abreviação do modulo@Ex.:SEG@3}'
       AND AM.DESCRICAO_APLICACAO_MODULO = '#{Descrição da aplicação do modulo@Ex.:CONSULTA@30}'
       AND AM.REGISTRO_EXCLUIDO = 'N'
       AND SM.REGISTRO_EXCLUIDO = 'N'; 

    -- NÂO POSSUI APLICACAO_MODULO E MODULO_SISTEMA
    IF (vCountAplicacaoModuloSistema = 0) THEN

        DBMS_OUTPUT.PUT_LINE('[INFO] Não foi localizado a aplicação modulo [#{Abreviação do modulo@Ex.:SEG@3}] '
                            ||'e modulo sistema [#{Descrição da aplicação do modulo@Ex.:CONSULTA@30}]');

        SELECT COUNT(*) 
          INTO vCountModuloSistema 
          FROM SEFAZ_SEG.TA_MODULO_SISTEMA SM 
         WHERE SM.ABREVIACAO_MODULO = '#{Abreviação do modulo@Ex.:SEG@3}'
           AND SM.REGISTRO_EXCLUIDO = 'N';

        IF (vCountModuloSistema = 0) THEN

            INSERT INTO SEFAZ_SEG.TA_MODULO_SISTEMA (
                IDENTIFICACAO_MODULO_SISTEMA,
                ABREVIACAO_MODULO,
                DESCRICAO_MODULO_SISTEMA,
                USUARIO_INSERCAO,
                DATA_INSERCAO)
            VALUES (
                SEFAZ_SEG.SQ_MODULO_SISTEMA.NEXTVAL,
                '#{Abreviação do modulo@Ex.:SEG@3}',
                '#{Descrição do modulo do sistema@Ex.:Cadastro de Contribuinte@60}',
                USER,
                CURRENT_TIMESTAMP);

            DBMS_OUTPUT.PUT_LINE('[SUCCESS] Modulo sistema [#{Abreviação do modulo@Ex.:SEG@3}] incluída com sucesso!');

        END IF;

        INSERT INTO SEFAZ_SEG.TA_APLICACAO_MODULO (
                IDENTIFICACAO_APLICACAO_MODULO,
                IDENTIFICACAO_MODULO_SISTEMA,
                DESCRICAO_APLICACAO_MODULO,
                USUARIO_INSERCAO,
                DATA_INSERCAO)
        SELECT  SEFAZ_SEG.SQ_APLICACAO_MODULO.NEXTVAL,
                IDENTIFICACAO_MODULO_SISTEMA,
                '#{Descrição da aplicação do modulo@Ex.:CONSULTA@30}',
                USER,
                CURRENT_TIMESTAMP
          FROM SEFAZ_SEG.TA_MODULO_SISTEMA MS
         WHERE MS.ABREVIACAO_MODULO = '#{Abreviação do modulo@Ex.:SEG@3}'
           AND MS.REGISTRO_EXCLUIDO = 'N';

        IF (SQL%ROWCOUNT > 0) THEN
            DBMS_OUTPUT.PUT_LINE('[SUCCESS] Aplicação modulo [Descrição da aplicação do modulo] incluída com sucesso!');
        ELSE
            DBMS_OUTPUT.PUT_LINE('[ERROR] Aplicação modulo [#{Descrição da aplicação do modulo@Ex.:CONSULTA@30}] não incluída!');
        END IF;

    END IF;

    INSERT INTO SEFAZ_SEG.TA_OPCAO_APLICACAO (
        IDENTIFICACAO_OPCAO_APLICACAO,
        IDENTIFICACAO_APLICACAO_MODULO,
        CASO_USO,
        OPCAO_URL,
        DESCRIPCAO_OPCAO,
        AJUDA_OPCAO,
        USUARIO_INSERCAO,
        DATA_INSERCAO)
    SELECT
        SEFAZ_SEG.SQ_OPCAO_APLICACAO.NEXTVAL,
        AM.IDENTIFICACAO_APLICACAO_MODULO,
        '#{Caso de uso@Ex.: SCEUC0003@10}',
        '#{URL opção@Ex.: /modules/seg/consulta/consulta-comunicacao-contribuinte.jsf@100}',
        '#{Descrição da opção@Ex.:Consultar comunicações com os contribuintes@60}',
        'https://satteste.sefaz.to.gov.br/jamwiki',
        USER,
        CURRENT_TIMESTAMP
     FROM SEFAZ_SEG.TA_APLICACAO_MODULO AM
    INNER JOIN SEFAZ_SEG.TA_MODULO_SISTEMA SM 
            ON AM.IDENTIFICACAO_MODULO_SISTEMA = SM.IDENTIFICACAO_MODULO_SISTEMA
    WHERE SM.ABREVIACAO_MODULO = '#{Abreviação do modulo@Ex.:SEG@3}'
      AND AM.DESCRICAO_APLICACAO_MODULO = '#{Descrição da aplicação do modulo@Ex.:CONSULTA@30}';

    IF (SQL%ROWCOUNT > 0) THEN
        DBMS_OUTPUT.PUT_LINE('[SUCCESS] Opção aplicação [#{Descrição da opção@Ex.:Consultar comunicações com os contribuintes@60}] incluída com sucesso!');
    ELSE
        DBMS_OUTPUT.PUT_LINE('[ERROR] Opção aplicação [#{Descrição da opção@Ex.:Consultar comunicações com os contribuintes@60}] não incluída!');
    END IF;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR (-20002,'OCORREU UM ERRO FOI EXECUTADO O ROLLBACK.' || sqlerrm);
END;
/

prompt "[LOG] >>>>>> Fim da execução do script de inserção na tabela SEFAZ_SEG.TA_OPCAO_APLICACAO"