package com.deportes.tiendadeportiva.services;

import com.deportes.tiendadeportiva.models.DetalleVenta;
import com.deportes.tiendadeportiva.models.Venta;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generarFacturaPdf(Venta venta) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Fuentes
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        try {
            // Cargar y agregar logo
            ClassPathResource logoResource = new ClassPathResource("static/images/logo-sportzone.jpg");
            ImageData imageData = ImageDataFactory.create(logoResource.getURL());
            Image logo = new Image(imageData);
            
            // Configurar tamaño y posición del logo
            logo.setWidth(150); // Ancho en puntos
            logo.setHeight(50); // Alto en puntos
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            
            document.add(logo);
            document.add(new Paragraph(" ")); // Espacio después del logo
            
        } catch (Exception e) {
            // Si no encuentra el logo, mostrar texto alternativo
            Paragraph tituloLogo = new Paragraph("🏆 SPORTZONE 🏆")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(tituloLogo);
        }

        // Título de la factura
        Paragraph title = new Paragraph("FACTURA DE VENTA")
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Espacio

        // Información de la empresa
        Paragraph empresa = new Paragraph("SportZone - Tu Tienda Deportiva")
                .setFont(boldFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(empresa);

        Paragraph eslogan = new Paragraph("Equipamiento Deportivo de Alta Calidad")
                .setFont(font)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(eslogan);

        Paragraph direccion = new Paragraph("Colombia, Bogotá - Centro Deportivo")
                .setFont(font)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(direccion);

        Paragraph contacto = new Paragraph("Tel: +57 1 1234567 | Email: info@sportzone.com")
                .setFont(font)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(contacto);

        document.add(new Paragraph(" ").setFontSize(8)); // Espacio

        // Información de la factura
        Table infoTable = new Table(2);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        infoTable.addCell(crearCelda("N° Factura:", true));
        infoTable.addCell(crearCelda(venta.getNumeroFactura(), false));
        
        infoTable.addCell(crearCelda("Fecha:", true));
        infoTable.addCell(crearCelda(
            venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), false));
        
        infoTable.addCell(crearCelda("Cliente:", true));
        infoTable.addCell(crearCelda(venta.getUsuario().getNombre(), false));
        
        infoTable.addCell(crearCelda("Email:", true));
        infoTable.addCell(crearCelda(venta.getUsuario().getEmail(), false));

        document.add(infoTable);
        document.add(new Paragraph(" "));

        // Tabla de productos
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 1, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados de tabla
        table.addHeaderCell(crearCelda("Artículo Deportivo", true));
        table.addHeaderCell(crearCelda("Deporte", true));
        table.addHeaderCell(crearCelda("Cantidad", true));
        table.addHeaderCell(crearCelda("Precio Unit.", true));
        table.addHeaderCell(crearCelda("Subtotal", true));

        // Detalles de la venta
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        for (DetalleVenta detalle : venta.getDetalles()) {
            table.addCell(crearCelda(detalle.getProducto().getNombre(), false));
            table.addCell(crearCelda(detalle.getProducto().getDeporte(), false));
            table.addCell(crearCelda(detalle.getCantidad().toString(), false));
            table.addCell(crearCelda(df.format(detalle.getPrecioUnitario()), false));
            table.addCell(crearCelda(df.format(detalle.getSubtotal()), false));
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Totales
        Table totalesTable = new Table(2);
        totalesTable.setWidth(UnitValue.createPercentValue(50));
        totalesTable.setMarginLeft(250f);

        totalesTable.addCell(crearCelda("Subtotal:", true));
        totalesTable.addCell(crearCelda(df.format(venta.getSubtotal()), false));
        
        totalesTable.addCell(crearCelda("IVA (19%):", true));
        totalesTable.addCell(crearCelda(df.format(venta.getIva()), false));
        
        totalesTable.addCell(crearCelda("TOTAL:", true));
        totalesTable.addCell(crearCelda(df.format(venta.getTotal()), true));

        document.add(totalesTable);

        // Información adicional deportiva
        document.add(new Paragraph(" "));
        Paragraph garantia = new Paragraph("✅ Garantía Deportiva: 6 meses en todos los artículos")
                .setFont(font)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(garantia);

        Paragraph cambios = new Paragraph("🔄 Cambios y devoluciones: 15 días para artículos sin uso")
                .setFont(font)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(cambios);

        document.add(new Paragraph(" "));

        // Pie de página
        Paragraph agradecimiento = new Paragraph("¡Gracias por elegir SportZone!")
                .setFont(boldFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(agradecimiento);

        Paragraph mensaje = new Paragraph("Que cada victoria comience con el equipo adecuado")
                .setFont(font)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(mensaje);

        Paragraph contacto2 = new Paragraph("Para consultas: soporte@sportzone.com | www.sportzone.com")
                .setFont(font)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(contacto2);

        document.close();
        return outputStream.toByteArray();
    }

    private Paragraph crearCelda(String texto, boolean negrita) {
        try {
            PdfFont font = negrita ? 
                PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD) :
                PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            return new Paragraph(texto)
                    .setFont(font)
                    .setFontSize(10)
                    .setMargin(0)
                    .setPadding(0);
        } catch (IOException e) {
            return new Paragraph(texto);
        }
    }
}