package ar.edu.utn.frc.tup.lc.iv.services;

import java.io.IOException;

public interface IQRService {
    /**
     * Genera un código QR para un visitante dado su número de documento.
     *
     * @param docNumber Número de documento del visitante.
     * @return URL de la imagen QR generada.
     * @throws IOException si ocurre un error al generar el código QR.
     */
    byte[] generateQrForVisitor(Long docNumber) throws IOException;
}