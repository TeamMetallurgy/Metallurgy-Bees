package elcon.mods.metallurgybees.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityMetadata extends TileEntityExtended {
	
	private int meta = 0;
	public boolean droppedBlock = false;

	public TileEntityMetadata() {
	}
	
	public TileEntityMetadata(int meta) {
		setTileMetadata(meta);
	}
	
	public TileEntityMetadata(Integer meta) {
		this(meta.intValue());
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, getTileMetadata(), nbt);
		return packet;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if (pkt != null && pkt.func_148857_g() != null)
		readFromNBT(pkt.func_148857_g());
    }
	
	
	public int getTileMetadata() {
		return meta;
	}

	public void setTileMetadata(int meta) {
		this.meta = meta;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		meta = nbt.getInteger("Metadata");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Metadata", meta);
	}
}
