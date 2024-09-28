package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QRService implements IQRService {

    @Autowired
    private VisitorRepository visitorsRepository;

    @Override
    public byte[] generateQrForVisitor(Long docNumber) throws IOException {
        // Busca al visitante en la base de datos
        VisitorEntity visitor = visitorsRepository.findByDocNumber(docNumber);

        if (visitor == null) {
            throw new IllegalArgumentException("No se encontró un visitante con el número de documento proporcionado.");
        }

        String qrData = "Nombre: " + visitor.getName() +
                ", Apellido: " + visitor.getLastName() +
                ", Documento: " + visitor.getDocNumber();

        // Generar el código QR utilizando ZXing
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 150, 150);

            // Convertir el código QR a bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException e) {
            throw new IOException("Error al generar el código QR: " + e.getMessage());
        }
    }
}
