package selim.penguins.penguin;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import selim.penguins.items.ItemApparel;
import selim.penguins.items.ItemApparel.EnumPenguinSlot;

public class EntityPenguin extends EntityAnimal {

	private static final DataParameter<NBTTagCompound> SLOTS = EntityDataManager
			.createKey(EntityPenguin.class, DataSerializers.COMPOUND_TAG);

	public EntityPenguin(World world) {
		super(world);
		this.setSize(0.6f, 1.25f);
		this.getDataManager().register(SLOTS, new NBTTagCompound());
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return super.getMaxSpawnedInChunk();
	}

	public ItemStack getSlot(EnumPenguinSlot slot) {
		NBTTagCompound slots = this.getDataManager().get(SLOTS);
		if (!slots.hasKey(slot.getId()))
			return ItemStack.EMPTY;
		return new ItemStack(slots.getCompoundTag(slot.getId()));
	}

	/**
	 * Sets the specified EnumPenguinSlot to the given ItemStack
	 * 
	 * @param slot
	 * @param stack
	 * @return Returns previous stack in given slot, or null if given stack is
	 *         not applicable
	 */
	public ItemStack setSlot(EnumPenguinSlot slot, ItemStack stack) {
		NBTTagCompound slots = this.getDataManager().get(SLOTS);
		if (stack == null || !(stack.getItem() instanceof ItemApparel)
				|| ((ItemApparel) stack.getItem()).getSlot() != slot)
			return null;
		ItemStack toReturn = new ItemStack(slots.getCompoundTag(slot.getId()));
		slots.setTag(slot.getId(), stack.serializeNBT());
		return toReturn;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setTag("slots", this.getDataManager().get(SLOTS));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.getDataManager().set(SLOTS, nbt.getCompoundTag("slots"));
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() instanceof ItemApparel) {
			ItemApparel apparel = (ItemApparel) stack.getItem();
			ItemStack prevStack = this.setSlot(apparel.getSlot(), stack);
			if (prevStack == null)
				return super.processInteract(player, hand);
			if (!player.capabilities.isCreativeMode)
				player.addItemStackToInventory(prevStack);
			return true;
		}
		return super.processInteract(player, hand);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
				.setBaseValue(0.15000000298023224D);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() instanceof ItemFishFood;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0,
				new EntityAIAvoidEntity<EntityPolarBear>(this, EntityPolarBear.class, 16, 0.5D, 1.5D));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.FISH, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityPenguin(ageable.world);
	}

}
