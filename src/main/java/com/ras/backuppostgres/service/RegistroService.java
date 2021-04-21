package com.ras.backuppostgres.service;

import com.ras.backuppostgres.model.Registro;
import com.ras.backuppostgres.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegistroService {
    @Autowired
    RegistroRepository registroRepository;

    public void startBackup() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\backups\\";
        String fileName = getFileName();
        String databaseName = "backup_postgres";

        System.out.println("filePath: " + filePath);
        System.out.println("fileName: " + fileName);

        List<String> cmds = new ArrayList<String>();
        cmds.add("C:\\Program Files\\PostgreSQL\\9.2\\bin\\pg_dump.exe");
        cmds.add("-i");
        cmds.add("-h");
        cmds.add("localhost");
        cmds.add("-p");
        cmds.add("5432");
        cmds.add("-U");
        cmds.add("postgres");
        cmds.add("-F");
        cmds.add("t");
        cmds.add("-b");
        cmds.add("-v");
        cmds.add("-f");
        cmds.add("\"" + filePath + fileName + "\"");
        cmds.add("\"" + databaseName + "\"");

        String line = "";
        for (String s : cmds) {
            line = line + " " + s;
        }

        System.out.println(line);

        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.environment().put("PGPASSWORD", "postgres");
        Process process = pb.start();

        novoRegistro(filePath, fileName);
        System.out.println("Backup finalizado com sucesso!!!");
    }

    private String getFileName() {
        SimpleDateFormat formatador = new SimpleDateFormat ("yyyyMMddHHmmss");
        return formatador.format(new Date()) + ".backup";
    }

    private void novoRegistro(String filePath, String fileName) {
        Registro reg = new Registro();
        reg.setData(new Date());
        reg.setFilePath("backups/");
        reg.setFileName(fileName);
        registroRepository.save(reg);
    }
}
