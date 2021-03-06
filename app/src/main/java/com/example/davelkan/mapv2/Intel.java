package com.example.davelkan.mapv2;

import com.example.davelkan.mapv2.util.Node;
import com.example.davelkan.mapv2.util.NodeMap;
import com.example.davelkan.mapv2.util.User;

import java.util.Random;

/**
 * Created by mwismer on 12/17/14.
 */
public class Intel {
    private Node node;
    private User user;

    public Intel(User user) {
        this.node = null;
        this.user = user;
    }

    public Node getNode() {
        return node;
    }
    public boolean empty(){
        return (getNode() == null);
    }

    public void setNode(Node node) {
        this.node = node;
    }
    public void removeNode() {
        this.node = null;
    }

    // gathering intel at allied node... checking node color may be unnecessary
    public String gatherIntel(Node node) {
        if (node == null) {
            return "Return to the node to gather intel!";
        } else if (node.getColor().equalsIgnoreCase(user.getColor())) {
            if (empty()) {
                //TODO: Fanciness - add fancy fake number generator
                setNode(node);
                return "Intel Gathered!  Deliver it to another WhisperSpot!";
            } else {
                return "You may only carry 1 intel at a time.";
            }
        } else {
            return "This is enemy territory! You have to decrypt intel here!";
        }
    }

//    deliver intel, ideally at a friendly node
//    will give the user a status bar in the node menu
    public String deliverIntel(Node activeNode, NodeMap nodes) {
        if (empty()) {
            return "You poor ignorant fool. You have no Intel to offer.";
        } else if (activeNode == null) {
            return "Return to the node to return intel!";
        } else if (this.node == activeNode) {
            return "This intel has no value here. Deliver it to another WhisperSpot";
        }
        else {
            int distance = (int) getNode().getDistance(activeNode.getCenter());

            int influence = 5 + distance / 200;
            activeNode.captureByPoints(user, influence, nodes);
            removeNode();
            return ("Nice Work Agent! You gained " + influence + " Influence over this WhisperSpot");
        }
    }


    //decrypt intel at enemy node
    public String decryptIntel(Node activeNode) {
        if (activeNode == null) {
            return "Return to the node to decrypt intel!";
        } else if (activeNode.getColor().equalsIgnoreCase(user.getEnemyColor())) {
            if (empty() && decryptCounter()) {
                Random rand = new Random();
                int odds = rand.nextInt(100);
                int ownership = activeNode.getOwnership();
                if (odds > ownership - 5) {
                    setNode(activeNode);
                    return "Intel Decrypted!  Deliver it to another WhisperSpot!";
                } else {
                    return "Your cover was blown while decrypting message! You need to lay low for a bit!";
                }
            } else if (!empty()) {
                return "You may only carry 1 intel at a time.";
            } else {
                return "Your cover is blown here! You need to lay low for a bit!";
            }
        } else {
            return "You don't need to decrypt intel at allied WhisperSpots";
        }
    }

    private boolean decryptCounter() {  //counter to stop users trying to decrypt too frequently
        return true;
    }
}
