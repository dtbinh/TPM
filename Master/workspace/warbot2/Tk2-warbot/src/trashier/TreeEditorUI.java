package trashier;

import java.awt.GridBagLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.turtlekit2.utils.XMLAttributes;


public class TreeEditorUI extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 1L;
	Document document;
	boolean compress = true;
	AdapterNode displayedNode;
	JTree xmlTree;
	JPanel textBoxPanel;
	
	public TreeEditorUI(Document document) {
		this.document = document;
		
		this.setLayout(new GridBagLayout());
		xmlTree = new JTree(new DomToTreeModelAdapter());
//		JScrollPane treeView = new JScrollPane(xmlTree);
		xmlTree.addTreeSelectionListener(this);
//		this.add(treeView);
		this.add(xmlTree);
//		textBoxPanel = new JPanel();
//		JScrollPane treeEdit = new JScrollPane(textBoxPanel);
//		this.add(treeEdit);
		this.validate();
	}




	boolean treeElement(String elementName) {
		for (int i=0; i<treeElementNames.length; i++) {
			if ( elementName.equals(treeElementNames[i]) ) return true;
		}
		return false;
	}

	public int getIndex(Node node){
		Element father = (Element)(node.getParentNode());
		NodeList fatherSons = father.getElementsByTagName(node.getNodeName());
		for(int i=0;i<fatherSons.getLength();i++){
			Node compareNode = fatherSons.item(i);
			if(compareNode == node) return i;
		}
		return 0;
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath p = e.getNewLeadSelectionPath();
		if (p != null) {
			AdapterNode adpNode = (AdapterNode) p.getLastPathComponent();
			adpNode.content();
		}
	}


	
	
	
	
	
	
	
	
	
	

	static final int ELEMENT_TYPE =   Node.ELEMENT_NODE;
	static final int ATTR_TYPE =      Node.ATTRIBUTE_NODE;
	static final int TEXT_TYPE =      Node.TEXT_NODE;
	static final int CDATA_TYPE =     Node.CDATA_SECTION_NODE;
	static final int ENTITYREF_TYPE = Node.ENTITY_REFERENCE_NODE;
	static final int ENTITY_TYPE =    Node.ENTITY_NODE;
	static final int PROCINSTR_TYPE = Node.PROCESSING_INSTRUCTION_NODE;
	static final int COMMENT_TYPE =   Node.COMMENT_NODE;
	static final int DOCUMENT_TYPE =  Node.DOCUMENT_NODE;
	static final int DOCTYPE_TYPE =   Node.DOCUMENT_TYPE_NODE;
	static final int DOCFRAG_TYPE =   Node.DOCUMENT_FRAGMENT_NODE;
	static final int NOTATION_TYPE =  Node.NOTATION_NODE;

	static final String[] treeElementNames = {
		"Simulations",
		"Simulation",
		"Flavors",
		"RandomFlavors",
		"Flavor",
		"Pools",
		"Pool",
		"Gene",
		"Agents",
		"Agent",
		"Parameters",
		"Managers",
		"Manager",
		"Viewers",
		"Viewer",
		"Observers",
		"Observer",
	};


	/** This class wraps a DOM node and returns the text we want to
    display in the tree. It also returns children, index values,
    and child counts. */
	@SuppressWarnings("unchecked")
    public class AdapterNode{
    	Node domNode;
    	XMLAttributes xmlTable;
		Hashtable<JTextField, JTextField> key_value = new Hashtable<JTextField, JTextField>();
    	Hashtable<JTextField, JTextField> value_key = new Hashtable<JTextField, JTextField>();
    	Hashtable<String, String> stringValue_key = new Hashtable<String, String>();
    	Hashtable<String, String> stringKey_value = new Hashtable<String, String>();
    	Hashtable keyRemove = new Hashtable();

    	public AdapterNode(org.w3c.dom.Node node) {
    		domNode = node;
    	}

    	public String toString() {
    		String s = "";
    		/*s = typeName[domNode.getNodeType()];*/
    		String nodeName = domNode.getNodeName();
    		if (! nodeName.startsWith("#")) {
    			s += " " + nodeName;
    		}
    		if (compress) {
    			String t = domNode.toString().trim();
    			int x = t.indexOf("\n");
    			if (x >= 0) t = t.substring(0, x);
    			s += " " + t;
    			return s;
    		}
    		return s;
//    		return nodeName;
    	}

    	public String content() {
    		displayedNode = this;
    		textBoxPanel.removeAll();
    		textBoxPanel.repaint();
    		String s = "";
    		NamedNodeMap attributes = domNode.getAttributes();
    		int listLength = attributes.getLength();
    		xmlTable = new XMLAttributes();
    		for(int k=0; k<listLength;k++) {
    			xmlTable.put(attributes.item(k).getNodeName(),((Element)domNode).getAttribute(attributes.item(k).getNodeName()));
    			JTextField key = new JTextField(attributes.item(k).getNodeName());
    			JTextField value = new JTextField(((Element)domNode).getAttribute(attributes.item(k).getNodeName()));

    			key_value.put(key, value);
    			value_key.put(value,key);
    			stringValue_key.put(value.getText(), key.getText());
    			stringKey_value.put(key.getText(),value.getText());
    			key.addCaretListener(new CaretListener(){
    				public void caretUpdate(CaretEvent e) {
    					JTextField localKey = (JTextField)e.getSource();
    					JTextField localObject = key_value.get(localKey);
    					String localOldKeyName = (stringValue_key.get(localObject.getText()));
    					((Element)domNode).removeAttribute(localOldKeyName);
    					((Element)domNode).setAttribute(localKey.getText(),localObject.getText());
    					stringValue_key.remove(stringKey_value.get(localOldKeyName));
    					stringKey_value.remove(localOldKeyName);
    					stringValue_key.put(localObject.getText(),localKey.getText());
    					stringKey_value.put(localKey.getText(),localObject.getText());
    					xmlTree.repaint();
    				}
    			}
    			);

    			value.addCaretListener(new CaretListener(){
    				public void caretUpdate(CaretEvent e) {
    					JTextField localObject = (JTextField)e.getSource();
    					JTextField localKey = value_key.get(localObject);
    					String localOldObjectName = (stringKey_value.get(localKey.getText()));
    					((Element)domNode).setAttribute(localKey.getText(),localObject.getText());
    					stringKey_value.remove(stringValue_key.get(localOldObjectName));
    					stringValue_key.remove(localOldObjectName);
    					stringValue_key.put(localObject.getText(),localKey.getText());
    					stringKey_value.put(localKey.getText(),localObject.getText());
    					xmlTree.repaint();
    				}
    			}
    			);

    			textBoxPanel.add(key);
    			textBoxPanel.add(value);

    		}
    		s+= xmlTable.toString();
    		return s;
    	}





    	/**
    	 * Return children, index, and count values
    	 */
    	public int index(AdapterNode child) {
    		//System.err.println("Looking for index of " + child);
    		int count = childCount();
    		for (int i=0; i<count; i++) {
    			AdapterNode n = this.child(i);
    			if (child.domNode == n.domNode) return i;
    		}
    		return -1; // Should never get here.
    	}

    	public AdapterNode child(int searchIndex) {
    		//Note: JTree index is zero-based.
    		Node node = domNode.getChildNodes().item(searchIndex);
    		if (compress) {
    			// Return Nth displayable node
    			int elementNodeIndex = 0;
    			for (int i=0; i<domNode.getChildNodes().getLength(); i++) {
    				node = domNode.getChildNodes().item(i);
    				if (node.getNodeType() == ELEMENT_TYPE && treeElement(node.getNodeName()) && elementNodeIndex++ == searchIndex) {
    					break;
    				}
    			}
    		}
    		return new AdapterNode(node);
    	}

    	public int childCount() {
    		if (!compress) {
    			// Indent this
    			return domNode.getChildNodes().getLength();
    		}
    		int count = 0;
    		for (int i=0; i<domNode.getChildNodes().getLength(); i++) {
    			Node node = domNode.getChildNodes().item(i);
    			if (node.getNodeType() == ELEMENT_TYPE && treeElement( node.getNodeName() )) {
    				++count;
    			}
    		}
    		return count;
    	}

    	public Node getNode(){
    		return domNode;
    	}
    }








    /**
     * Utility class for DOM to Tree.
     * */
	
    public class DomToTreeModelAdapter implements javax.swing.tree.TreeModel {

    	public Object  getRoot() {
    		//System.err.println("Returning root: " +document);
    		return new AdapterNode(document);
    	}
    	public boolean isLeaf(Object aNode) {
    		AdapterNode node = (AdapterNode) aNode;
    		if (node.childCount() > 0) return false;
    		return true;
    	}
    	public int getChildCount(Object parent) {
    		AdapterNode node = (AdapterNode) parent;
    		return node.childCount();
    	}
    	public Object getChild(Object parent, int index) {
    		AdapterNode node = (AdapterNode) parent;
    		return node.child(index);
    	}
    	public int getIndexOfChild(Object parent, Object child) {
    		AdapterNode node = (AdapterNode) parent;
    		return node.index((AdapterNode) child);
    	}
    	public void valueForPathChanged(TreePath path, Object newValue) {

    	}

    	private Vector<TreeModelListener> listenerList = new Vector<TreeModelListener>();
    	public void addTreeModelListener(TreeModelListener listener) {
    		if ( listener != null
    				&& ! listenerList.contains( listener ) ) {
    			listenerList.addElement( listener );
    		}
    	}
    	public void removeTreeModelListener(TreeModelListener listener) {
    		if ( listener != null ) {
    			listenerList.removeElement( listener );
    		}
    	}



    	public void fireTreeNodesChanged( TreeModelEvent e ) {
    		Enumeration<TreeModelListener> listeners = listenerList.elements();
    		while ( listeners.hasMoreElements() ) {
    			TreeModelListener listener =
    				listeners.nextElement();
    			listener.treeNodesChanged( e );
    		}
    	}
    	public void fireTreeNodesInserted( TreeModelEvent e ) {
    		Enumeration<TreeModelListener> listeners = listenerList.elements();
    		while ( listeners.hasMoreElements() ) {
    			TreeModelListener listener =
    				listeners.nextElement();
    			listener.treeNodesInserted( e );
    		}
    	}
    	public void fireTreeNodesRemoved( TreeModelEvent e ) {
    		Enumeration<TreeModelListener> listeners = listenerList.elements();
    		while ( listeners.hasMoreElements() ) {
    			TreeModelListener listener =
    				listeners.nextElement();
    			listener.treeNodesRemoved( e );
    		}
    	}
    	public void fireTreeStructureChanged( TreeModelEvent e ) {
    		Enumeration<TreeModelListener> listeners = listenerList.elements();
    		while ( listeners.hasMoreElements() ) {
    			TreeModelListener listener =
    				listeners.nextElement();
    			listener.treeStructureChanged( e );
    		}
    	}
    }
}
