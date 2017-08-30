package de.verti.toolkit.jpwgen.flags;

import de.verti.toolkit.jpwgen.IDefaultFilter;
import de.verti.toolkit.jpwgen.IPwFlag;


public abstract class AbstractPwFlag implements IPwFlag, IDefaultFilter {

	/**
	 */
	private static final long serialVersionUID = 1L;

	protected Long mask = 0L;

	@Override
	public Long mask(final Long flags) {
		return flags | mask;
	}

	@Override
	public Long unmask(final Long flags) {
		return flags & (~mask);
	}

	@Override
	public Long getMask() {
		return mask;
	}

	@Override
	public boolean isMasked(final Long flags) {
		return (flags & mask) != 0;
	}

	@Override
	public String getId() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return "AbstractPwFlag [mask=" + mask + "]";
	}

}
