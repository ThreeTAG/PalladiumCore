package net.threetag.palladiumcore.network;

public abstract class MessageC2S extends Message {

    /**
     * Sends message to every player online
     */
    public void send() {
        this.getType().getNetworkManager().sendToServer(this);
    }

}
