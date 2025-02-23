package software.developer.bhushan.components;

import software.developer.bhushan.font.FontUtils;

import javax.swing.*;
import java.awt.*;

public class FormPasswordField {
    private JLabel label;
    private JPasswordField field;
    private GridBagLayout formFieldGridBagLayout = new GridBagLayout();
    private JPanel formFieldPanel = new JPanel(formFieldGridBagLayout);
    private GridBagConstraints formFieldGridBagConstraints = new GridBagConstraints();

    public  FormPasswordField(){
        this.label = new JLabel();
        this.field = new JPasswordField();
        label.setFont(FontUtils.Heading_2_Plain);
        field.setFont(FontUtils.Heading_2_Plain);
    }
    public FormPasswordField(JLabel label, JPasswordField field) {
        this.label = label;
        this.field = field;
    }

    public JPanel create() {
        formFieldGridBagConstraints.insets = new Insets(5, 5, 5, 5);
//        gbc.anchor = GridBagConstraints.WEST;

        // Add Label
        formFieldGridBagConstraints.gridx = 0;
        formFieldGridBagConstraints.gridy = 0;
        formFieldPanel.add(label, formFieldGridBagConstraints);

        // Add Field
        formFieldGridBagConstraints.gridx = 1;
        formFieldPanel.add(field, formFieldGridBagConstraints);

        return formFieldPanel;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JPasswordField getField() {
        return field;
    }

    public void setField(JPasswordField field) {
        this.field = field;
    }

    public GridBagLayout getFormFieldGridBagLayout() {
        return formFieldGridBagLayout;
    }

    public void setFormFieldGridBagLayout(GridBagLayout formFieldGridBagLayout) {
        this.formFieldGridBagLayout = formFieldGridBagLayout;
    }

    public JPanel getFormFieldPanel() {
        return formFieldPanel;
    }

    public void setFormFieldPanel(JPanel formFieldPanel) {
        this.formFieldPanel = formFieldPanel;
    }

    public GridBagConstraints getFormFieldGridBagConstraints() {
        return formFieldGridBagConstraints;
    }

    public void setFormFieldGridBagConstraints(GridBagConstraints formFieldGridBagConstraints) {
        this.formFieldGridBagConstraints = formFieldGridBagConstraints;
    }
}
