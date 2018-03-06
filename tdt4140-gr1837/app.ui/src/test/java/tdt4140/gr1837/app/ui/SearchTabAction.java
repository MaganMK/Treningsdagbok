package tdt4140.gr1837.app.ui;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class SearchTabAction extends GuiTest {

	public SearchTabAction() {
		getRootNode();
	}

	@Override
	protected Parent getRootNode() {
		Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("searchTab.fxml"));
            return parent;
        } catch (IOException ex) {
            // TODO ...
        }
        return parent;
	}
	
	//Tester naar man skriver inn snow og trykker paa knappen, men burde endres
	@Test
    public void testSearchAction() {
        TextField textField = find("#searchField");
        String text = "snow";
        click(textField).type(text);
        click("#searchButton");
        assertEquals("snow", textField.getText());
    }

}
