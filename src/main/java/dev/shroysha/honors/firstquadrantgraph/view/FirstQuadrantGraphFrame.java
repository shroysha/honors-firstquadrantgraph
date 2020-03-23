package dev.shroysha.honors.firstquadrantgraph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class FirstQuadrantGraphFrame extends JPanel {

    private int xBuffer, yBuffer;
    private double[] xPoints, yPoints;
    private Color backgroundColor, drawColor;
    private String title, xAxisLabel, yAxisLabel;
    private double xPixelLength, yPixelLength;
    private Double xAxisIncrements, yAxisIncrements;
    private Integer axisThickness;
    private double highestXPoint, highestYPoint;

    public FirstQuadrantGraphFrame(int sizeX, int sizeY) {

        this.setSize(sizeX, sizeY);
        this.setPreferredSize(new Dimension(sizeX, sizeY));
        backgroundColor = null;
        drawColor = null;
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        boolean axisDrawn;
        if (drawColor != null) {
            xBuffer = this.getWidth() / 15 + 5;
            yBuffer = this.getHeight() / 15 + 5;

            // Draws Axises
            g.setColor(drawColor);
//            g2d.setStroke(new BasicStroke(Objects.requireNonNullElse(axisThickness, 3)));

            g.drawLine(xBuffer, this.getHeight() - yBuffer, this.getWidth() - xBuffer,
                    this.getHeight() - yBuffer);     // Draws xAxis

            g.drawLine(xBuffer, yBuffer, xBuffer, this.getHeight() - yBuffer);  // Draws yAxis


            axisDrawn = true;

            g2d.setStroke(new BasicStroke());
        } else {
            axisDrawn = false;
        }
        try {
            if (xPoints != null && yPoints != null && axisDrawn) {
                if (xPoints.length != yPoints.length) {
                    throw new DifferentNumberOfPointsException();
                }

                for (int i = 0; i < xPoints.length; i++) { //Finds lowest and highest xs and ys for scaling
                    if (xPoints[i] > highestXPoint)
                        highestXPoint = xPoints[i];

                    if (yPoints[i] > highestYPoint)
                        highestYPoint = yPoints[i];
                }

                xPixelLength = (this.getWidth() - xBuffer * 2) / highestXPoint;
                yPixelLength = (this.getHeight() - yBuffer * 2) / highestYPoint;

                int pointDiameter;
                if (this.getWidth() > this.getHeight()) {
                    pointDiameter = this.getHeight() / 150 + 1;
                } else {
                    pointDiameter = this.getWidth() / 150 + 1;
                }

                // Draws Points
                int xPosition, yPosition;
                for (int i = 0; i < xPoints.length; i++) {
                    xPosition = (int) (xBuffer + xPoints[i] * xPixelLength) - pointDiameter;
                    yPosition = this.getHeight() - (int) (yBuffer + yPoints[i] * yPixelLength) - pointDiameter;

                    g.fillOval(xPosition, yPosition, pointDiameter * 2, pointDiameter * 2);
                }
            }
        } catch (DifferentNumberOfPointsException e) {
            System.out.println(e.getErrorMessage());
        }

        if (xAxisIncrements != null && axisDrawn) {
            int xPosition, yPosition, lineLength;
            for (int i = 0; i <= (highestXPoint / xAxisIncrements); i++) {
                xPosition = (int) (xBuffer + i * xAxisIncrements * xPixelLength);
                lineLength = this.getHeight() / 200 + 2;
                yPosition = this.getHeight() - (yBuffer + lineLength);

                g.drawLine(xPosition, yPosition, xPosition, yPosition + 2 * lineLength);
            }
        }

        if (yAxisIncrements != null && axisDrawn) {
            int xPosition, yPosition, lineLength;
            for (int i = 0; i <= (highestYPoint / yAxisIncrements); i++) {
                lineLength = this.getWidth() / 200 + 2;
                xPosition = xBuffer - lineLength;
                yPosition = (this.getHeight() - (int) (yBuffer + i * yAxisIncrements * yPixelLength));

                g.drawLine(xPosition, yPosition, xPosition + 2 * lineLength, yPosition);
            }
        }

        if (title != null) {
            int suggestedFontSize = yBuffer / 2;
            g.setFont(new Font("Monospaced", Font.PLAIN, suggestedFontSize));
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(xAxisLabel);
            int stringXLocation = this.getWidth() / 2 - stringWidth / 2;
            int stringYLocation = (yBuffer / 2 - fm.getDescent() / 2);

            g.drawString(title, stringXLocation, stringYLocation);
        }

        if (xAxisLabel != null && axisDrawn) {
            int suggestedFontSize = yBuffer / 2;
            g.setFont(new Font("Monospaced", Font.PLAIN, suggestedFontSize));
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(xAxisLabel);
            int stringXLocation = this.getWidth() / 2 - stringWidth / 2;
            int stringYLocation = this.getHeight() - (yBuffer / 2 - fm.getAscent() / 2);

            g.drawString(xAxisLabel, stringXLocation, stringYLocation);
        }

        if (yAxisLabel != null && axisDrawn) {
            int suggestedFontSize = xBuffer / 2;
            g.setFont(new Font("Monospaced", Font.PLAIN, suggestedFontSize));
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.getDescent();
            int stringXLocation = xBuffer / 2 + stringWidth / 2;
            int stringHeight = fm.stringWidth(yAxisLabel);

            int stringYLocation = this.getHeight() / 2 - (-stringHeight / 2);
            AffineTransform at = AffineTransform.getRotateInstance(-Math.PI / 2, stringXLocation, stringYLocation);

            g2d.setTransform(at);
            g2d.drawString(yAxisLabel, stringXLocation, stringYLocation);
        }

        g2d.dispose();
    }


    public void setXPoints(double[] xValues) {
        xPoints = xValues;
        this.repaint();
    }

    public void setYPoints(double[] yValues) {
        yPoints = yValues;
        this.repaint();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
        this.repaint();
    }

    public void setTitle(String theTitle) {
        title = theTitle;
        this.repaint();
    }

    public void setXAxisLabel(String xLabel) {
        xAxisLabel = xLabel;
        this.repaint();
    }

    public void setYAxisLabel(String yLabel) {
        yAxisLabel = yLabel;
        this.repaint();
    }

    public void setXAxisIncrements(double axisIncrements) {
        xAxisIncrements = axisIncrements;
        this.repaint();
    }

    public void setYAxisIncrements(double axisIncrements) {
        yAxisIncrements = axisIncrements;
        this.repaint();
    }

    public void showXNumbers() {
        this.repaint();

    }

    public void showYNumbers() {
        this.repaint();
    }

    public void setAxisThickness(Integer thickness) {
        axisThickness = thickness;
    }

    public static class DifferentNumberOfPointsException extends Exception {

        private final Exception e;

        public DifferentNumberOfPointsException() {
            e = new Exception("Exception: Different number of x and y values");
        }


        public String getErrorMessage() {
            return e.getMessage();
        }
    }
}

