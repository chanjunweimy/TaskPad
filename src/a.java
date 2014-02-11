import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;


public class a extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text;

	public static void main(String[] args){
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    a calc = new a(shell, SWT.NONE);
	    calc.pack();
	    shell.pack();
	    shell.open();
	    while(!shell.isDisposed()){
	        if(!display.readAndDispatch()) display.sleep();
	    }
	}

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public a(Composite parent, int style) {
		super(parent, style);
		setEnabled(false);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(55, 230, 352, 21);
		toolkit.adapt(text, true, true);
		
		TextViewer textViewer = new TextViewer(this, SWT.BORDER);
		StyledText styledText = textViewer.getTextWidget();
		styledText.setBounds(55, 20, 352, 176);
		toolkit.paintBordersFor(styledText);

	}
}
