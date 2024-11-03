package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QRServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private QRService qrService;

    private VisitorEntity visitor;

    @BeforeEach
    public void setUp() {
        visitor = new VisitorEntity();
        visitor.setName("Juan");
        visitor.setLastName("Pérez");
        visitor.setDocNumber(12345678L);
    }

    @Test
    public void generateQrForVisitorSuccess() throws IOException {
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(visitor);

        byte[] qrCode = qrService.generateQrForVisitor(12345678L);

        assertNotNull(qrCode);
        assertTrue(qrCode.length > 0);
        verify(visitorRepository, times(1)).findByDocNumber(12345678L);
    }

    @Test
    public void generateQrForVisitorVisitorNotFound() {
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            qrService.generateQrForVisitor(12345678L);
        });

        assertEquals("No se encontró un visitante con el número de documento proporcionado.", exception.getMessage());
        verify(visitorRepository, times(1)).findByDocNumber(12345678L);
    }

    @Test
    public void generateQrForVisitorWriterException() throws Exception {
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(visitor);

        try (MockedConstruction<QRCodeWriter> mocked = mockConstruction(QRCodeWriter.class,
                (mock, context) -> {
                    when(mock.encode(anyString(), any(BarcodeFormat.class), anyInt(), anyInt()))
                            .thenThrow(new WriterException("Error en la generación del QR"));
                })) {

            Exception exception = assertThrows(IOException.class, () -> {
                qrService.generateQrForVisitor(12345678L);
            });

            assertEquals("Error generating QR code.", exception.getMessage());
            verify(visitorRepository, times(1)).findByDocNumber(12345678L);
        }
    }
}