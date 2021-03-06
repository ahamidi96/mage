

/*
 * DraftGrid.java
 *
 * Created on 7-Jan-2011, 6:23:39 PM
 */

package mage.client.cards;

import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.CardViewRarityComparator;
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.client.util.audio.AudioManager;
import mage.constants.Constants;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DraftGrid extends javax.swing.JPanel implements MouseListener {

    private static final Logger logger = Logger.getLogger(DraftGrid.class);

    protected final CardEventSource cardEventSource = new CardEventSource();
    protected BigCard bigCard;
    protected MageCard markedCard;
    protected boolean emptyGrid;

    /**
     * Creates new form DraftGrid
     */
    public DraftGrid() {
        initComponents();
        markedCard = null;
        emptyGrid = true;
    }

    public void clear() {
        markedCard = null;
        this.clearCardEventListeners();
        for (Component comp : getComponents()) {
            if (comp instanceof Card || comp instanceof MageCard) {
                this.remove(comp);
            }
        }
    }

    public void loadBooster(CardsView booster, BigCard bigCard) {
        if (booster instanceof CardsView && booster.isEmpty()) {
            emptyGrid = true;
        } else {
            if (!emptyGrid) {
                AudioManager.playOnDraftSelect();
            }
            emptyGrid = false;
        }
        this.bigCard = bigCard;
        this.removeAll();

        int maxRows = 4;

        int numColumns = 5;
        int curColumn = 0;
        int curRow = 0;
        int offsetX = 5;
        int offsetY = 3;

        CardDimensions cardDimension = null;
        int maxCards;
        double scale;

        for (int i = 1; i < maxRows; i++) {
            scale = (double) (this.getHeight() / i) / Constants.FRAME_MAX_HEIGHT;
            cardDimension = new CardDimensions(scale);
            maxCards = this.getWidth() / (cardDimension.getFrameWidth() + offsetX);
            if ((maxCards * i) >= booster.size()) {
                numColumns = booster.size() / i;
                if (booster.size() % i > 0) {
                    numColumns++;
                }
                break;
            }
        }

        if (cardDimension != null) {
            Rectangle rectangle = new Rectangle(cardDimension.getFrameWidth(), cardDimension.getFrameHeight());
            Dimension dimension = new Dimension(cardDimension.getFrameWidth(), cardDimension.getFrameHeight());

            List<CardView> sortedCards = new ArrayList<>(booster.values());
            sortedCards.sort(new CardViewRarityComparator());
            for (CardView card : sortedCards) {
                MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, dimension, null, true, true, PreferencesDialog.getRenderMode(), true);
                cardImg.addMouseListener(this);
                add(cardImg);
                cardImg.update(card);
                rectangle.setLocation(curColumn * (cardDimension.getFrameWidth() + offsetX) + offsetX, curRow * (rectangle.height + offsetY) + offsetY);

                cardImg.setBounds(rectangle);
                cardImg.setCardBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                curColumn++;
                if (curColumn == numColumns) {
                    curColumn = 0;
                    curRow++;
                }
            }
            repaint();
        } else {
            logger.warn("Draft Grid - no possible fit of cards");
        }
    }

    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    private void hidePopup() {
        Plugins.instance.getActionCallback().mouseExited(null, null);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void mouseClicked(MouseEvent e) {
        if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0)) { // double clicks and repeated double clicks
            if (e.getButton() == MouseEvent.BUTTON1) {
                Object obj = e.getSource();
                if (obj instanceof MageCard) {
                    this.cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.PICK_A_CARD);
                    this.hidePopup();
                    AudioManager.playOnDraftSelect();
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) { // left or right click
            Object obj = e.getSource();
            if (obj instanceof MageCard) {
                if (this.markedCard != null) {
                    markedCard.setSelected(false);
                }
                this.cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.MARK_A_CARD);
                markedCard = ((MageCard) obj);
                markedCard.setSelected(true);
                repaint();
            }
        }

    }

    public boolean isEmptyGrid() {
        return emptyGrid;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
