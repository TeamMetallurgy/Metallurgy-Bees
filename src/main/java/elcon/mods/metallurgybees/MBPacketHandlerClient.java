package elcon.mods.metallurgybees;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;

// TODO: Fix Networking
@SideOnly(Side.CLIENT)
public class MBPacketHandlerClient /*implements IPacketHandler*/ {

	/*@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		byte packetID = dat.readByte();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			handlePacketClient(packetID, dat);
		}
	}*/
	
	private void handlePacketClient(byte packetID, ByteArrayDataInput dat) {
		World world = Minecraft.getMinecraft().theWorld;
		switch(packetID) {
		case 0:
			handlePacketTileMetadata(world, dat);
		}
	}

	private void handlePacketTileMetadata(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityMetadata();
			world.setTileEntity(x, y, z, tile);
		}
		tile.setTileMetadata(dat.readInt());
		world.markBlockForUpdate(x, y, z);
	}
}
